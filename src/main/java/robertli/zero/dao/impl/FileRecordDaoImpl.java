/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.ImagePathService;
import robertli.zero.dao.FileRecordDao;
import robertli.zero.dao.FileRecordTokenDao;
import robertli.zero.entity.FileRecord;
import robertli.zero.entity.FileRecordToken;

/**
 *
 * @version 2017-03-20 1.0.1
 * @author Robert Li
 */
@Component("fileRecordDao")
public class FileRecordDaoImpl extends GenericHibernateDao<FileRecord, String> implements FileRecordDao {

    @Resource
    private ImagePathService imagePathService;

    @Resource
    private FileRecordTokenDao fileRecordTokenDao;

    @Override
    public FileRecord saveFileRecord(String url) {
        final String uuid = imagePathService.pickImageId(url);
        FileRecordToken token = fileRecordTokenDao.get(uuid);
        if (token == null) {
            throw new RuntimeException("FileRecordDaoImpl saveFileRecord fail: can't found token for this uuid:" + uuid);
        } else if (uuid.length() != 36) {
            throw new RuntimeException("FileRecordDaoImpl saveFileRecord fail: wrong uuid:" + uuid);
        }
        FileRecord record = new FileRecord();
        record.setUrl(url);
        record.setUuid(uuid);
        save(record);
        token.setFileRecord(record);
        return record;
    }

    private boolean isEqualsRecord(FileRecord currentRecord, String newUrl) {
        if (currentRecord == null && newUrl == null) {
            return true;
        } else if (currentRecord != null && currentRecord.getUrl().equals(newUrl)) {
            return true;
        }
        return false;
    }

    @Override
    public FileRecord replaceFileRecord(FileRecord currentRecord, String newUrl) {
        if (isEqualsRecord(currentRecord, newUrl)) {
            return currentRecord;
        }
        if (currentRecord != null) {
            delete(currentRecord);
        }
        if (newUrl != null) {
            return saveFileRecord(newUrl);
        }
        return null;
    }

}

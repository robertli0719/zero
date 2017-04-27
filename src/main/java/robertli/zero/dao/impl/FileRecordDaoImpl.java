/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.dao.FileRecordDao;
import robertli.zero.dao.FileRecordTokenDao;
import robertli.zero.entity.FileRecord;
import robertli.zero.entity.FileRecordToken;

/**
 *
 * @version 2017-04-12 1.0.2
 * @author Robert Li
 */
@Component("fileRecordDao")
public class FileRecordDaoImpl extends GenericHibernateDao<FileRecord, String> implements FileRecordDao {

    @Resource
    private FileRecordTokenDao fileRecordTokenDao;

    @Override
    public FileRecord saveFileRecord(String uuid, String url) {
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

    private boolean isEqualsRecord(FileRecord currentRecord, String newUuid) {
        if (currentRecord == null && newUuid == null) {
            return true;
        } else if (currentRecord != null && currentRecord.getUuid().equals(newUuid)) {
            return true;
        }
        return false;
    }

    @Override
    public FileRecord replaceFileRecord(FileRecord currentRecord, String newUuid, String newUrl) {
        if (isEqualsRecord(currentRecord, newUuid)) {
            return currentRecord;
        }
        if (currentRecord != null) {
            delete(currentRecord);
        }
        if (newUuid != null) {
            return saveFileRecord(newUuid, newUrl);
        }
        return null;
    }

}

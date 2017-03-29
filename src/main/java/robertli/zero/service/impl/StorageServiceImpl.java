/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.FileManager;
import robertli.zero.dao.FileRecordDao;
import robertli.zero.dao.FileRecordTokenDao;
import robertli.zero.entity.FileRecord;
import robertli.zero.entity.FileRecordToken;
import robertli.zero.service.StorageService;

/**
 *
 * @version 1.0.3 2017-03-15
 * @author Robert Li
 */
@Component("storageService")
public class StorageServiceImpl implements StorageService {

    private static final int TEMP_FILE_LIFE_MINUTE = 60 * 24;

    @Resource
    private FileManager fileManager;

    @Resource
    private FileRecordTokenDao fileRecordTokenDao;

    @Resource
    private FileRecordDao fileRecordDao;

    @Override
    public String getContentType(String uuid) {
        FileRecordToken token = fileRecordTokenDao.get(uuid);
        if (token == null) {
            return null;
        }
        return token.getType();
    }

    @Override
    public String getFileName(String uuid) {
        FileRecordToken token = fileRecordTokenDao.get(uuid);
        if (token == null) {
            return null;
        }
        return token.getName();
    }

    @Override
    public byte[] get(String uuid) {
        FileRecordToken token = fileRecordTokenDao.get(uuid);
        if (token == null) {
            return new byte[0];
        }
        return fileManager.read(uuid);
    }

    @Override
    public String register(String name, String type) {
        FileRecordToken token = fileRecordTokenDao.saveFileRecord(name, type);
        return token.getUuid();
    }

    @Override
    public void store(String uuid, byte[] data) throws IOException {
        fileManager.write(uuid, data);
    }

    @Override
    public void clean() {
        List<FileRecordToken> tokenList = fileRecordTokenDao.listOverdueFileRecord(TEMP_FILE_LIFE_MINUTE);
        for (FileRecordToken token : tokenList) {
            String uuid = token.getUuid();
            fileManager.delete(uuid);
            fileRecordTokenDao.delete(token);
        }
    }

    @Override
    public void fix(String uuid) {
        FileRecordToken token = fileRecordTokenDao.get(uuid);
        FileRecord fileRecord = fileRecordDao.get(uuid);
        token.setFileRecord(fileRecord);
    }

}

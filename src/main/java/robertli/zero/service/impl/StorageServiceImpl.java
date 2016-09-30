/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.FileManager;
import robertli.zero.dao.FileRecordDao;
import robertli.zero.entity.FileRecord;
import robertli.zero.service.StorageService;

/**
 *
 * @version 1.02 2016-09-29
 * @author Robert Li
 */
@Component("storageService")
public class StorageServiceImpl implements StorageService {

    private static final int TEMP_FILE_LIFE_MINUTE = 60 * 24;

    @Resource
    private FileManager fileManager;

    @Resource
    private FileRecordDao fileRecordDao;

    @Override
    public FileRecord getFileRecord(String uuid) {
        return fileRecordDao.get(uuid);
    }

    @Override
    public byte[] get(String uuid) {
        getFileRecord(uuid).setLastAccessDate(new Date());
        return fileManager.read(uuid);
    }

    @Override
    public String register(String name, String type) {
        FileRecord fileRecord = fileRecordDao.saveFileRecord(name, type);
        return fileRecord.getUuid();
    }

    @Override
    public void store(String uuid, byte[] data) throws IOException {
        fileManager.write(uuid, data);
    }

    @Override
    public void delete(String uuid) {
        fileRecordDao.get(uuid).setTemp(true);
    }

    @Override
    public void clean() {
        List<FileRecord> fileRecordList = fileRecordDao.listOverdueFileRecord(TEMP_FILE_LIFE_MINUTE);
        for (FileRecord fileRecord : fileRecordList) {
            String uuid = fileRecord.getUuid();
            fileManager.delete(uuid);
            fileRecordDao.delete(fileRecord);
        }
    }

    @Override
    public void fix(String uuid) {
        fileRecordDao.get(uuid).setTemp(false);
    }

}

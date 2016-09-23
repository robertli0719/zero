/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.FileManager;
import robertli.zero.dao.FileRecordCacheDao;
import robertli.zero.dao.FileRecordDao;
import robertli.zero.entity.FileRecord;
import robertli.zero.entity.FileRecordCache;
import robertli.zero.service.StorageService;

/**
 *
 * @version 1.0 2016-09-22
 * @author Robert Li
 */
@Component("storageService")
public class StorageServiceImpl implements StorageService {

    @Resource
    private FileManager fileManager;

    @Resource
    private FileRecordDao fileRecordDao;

    @Resource
    private FileRecordCacheDao fileRecordCacheDao;

    private void moveFileDataFromCache() {
        List<FileRecordCache> fileRecordCacheList = fileRecordCacheDao.list();

        for (FileRecordCache cache : fileRecordCacheList) {
            String uuid = cache.getFile().getUuid();
            try {
                fileManager.write(uuid, cache.getContent());
            } catch (IOException ex) {
                Logger.getLogger(StorageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException("Fail to write file for fileManager");
            }
            fileRecordCacheDao.delete(cache);
        }
    }

    private void clearRemovedFile() {
        List<FileRecord> fileRecordList = fileRecordDao.listRemovedFile();
        for (FileRecord fileRecord : fileRecordList) {
            String uuid = fileRecord.getUuid();
            FileRecordCache cache = fileRecordCacheDao.get(uuid);
            if (cache != null) {
                fileRecordCacheDao.delete(cache);
                continue;
                //There could be other threads are writing this file to file system at the same time,
                //so we have to clear other info in next transaction.
            }
            if (fileManager.read(uuid) != null) {
                fileManager.delete(uuid);
            }

            fileRecordDao.delete(fileRecord);
        }
    }

    @Override
    public FileRecord getFileRecord(String uuid) {
        moveFileDataFromCache();
        FileRecord fileRecord = fileRecordDao.get(uuid);
        if (fileRecord == null) {
            return null;
        }
        fileRecord.setLastAccessDate(new Date());
        return fileRecord;
    }

    @Override
    public byte[] get(String uuid) {
        FileRecord fileRecord = getFileRecord(uuid);
        if (fileRecord == null) {
            return null;
        }
        return fileManager.read(uuid);
    }

    @Override
    public String add(String name, String type, byte[] data) {
        clearRemovedFile();
        FileRecord fileRecord = fileRecordDao.saveFileRecord(name, type);
        fileRecordCacheDao.saveFileRecordCache(fileRecord, data);
        return fileRecord.getUuid();
    }

    @Override
    public void delete(String uuid) {
        FileRecord fileRecord = fileRecordDao.get(uuid);
        fileRecord.setRemoved(true);
    }

}

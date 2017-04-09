/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.FileManager;
import robertli.zero.dao.FileRecordDao;
import robertli.zero.dao.FileRecordTokenDao;
import robertli.zero.dto.FileRecordDto;
import robertli.zero.entity.FileRecord;
import robertli.zero.entity.FileRecordToken;
import robertli.zero.service.StorageService;

/**
 *
 * @version 1.0.4 2017-04-07
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
    public FileRecordDto getFileRecord(String uuid) {
        final FileRecordToken token = fileRecordTokenDao.get(uuid);
        if (token == null) {
            return null;
        }
        FileRecordDto dto = new FileRecordDto();
        dto.setContentType(token.getType());
        dto.setFileName(token.getName());
        dto.setSize(token.getLen());
        return dto;
    }

    @Override
    public InputStream getInputStream(String uuid, long start) {
        FileRecordToken token = fileRecordTokenDao.get(uuid);
        if (token == null) {
            return new InputStream() {
                @Override
                public int read() throws IOException {
                    return -1;
                }
            };
        }
        return fileManager.getInputStream(uuid, start);

    }

    @Override
    public String register(String name, String type, long length) {
        FileRecordToken token = fileRecordTokenDao.saveFileRecord(name, type, length);
        return token.getUuid();
    }

    @Override
    public void store(String uuid, InputStream in, long length) throws IOException {
        fileManager.write(uuid, in, length);
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

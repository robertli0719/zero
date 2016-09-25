/*
 * The MIT License
 *
 * Copyright 2016 Robert Li.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
 * @version 1.01 2016-09-22
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

}

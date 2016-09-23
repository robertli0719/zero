/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.FileRecordCacheDao;
import robertli.zero.entity.FileRecord;
import robertli.zero.entity.FileRecordCache;

/**
 *
 * @author Robert Li
 */
@Component("fileRecordCacheDao")
public class FileRecordCacheDaoImpl extends GenericHibernateDao<FileRecordCache, String> implements FileRecordCacheDao {

    @Override
    public FileRecordCache saveFileRecordCache(FileRecord fileRecord, byte[] content) {
        FileRecordCache fileRecordCache = new FileRecordCache();
        fileRecordCache.setFile(fileRecord);
        fileRecordCache.setContent(content);
        save(fileRecordCache);
        return fileRecordCache;
    }

}

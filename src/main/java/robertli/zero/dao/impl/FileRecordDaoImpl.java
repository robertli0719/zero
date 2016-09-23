/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.dao.FileRecordDao;
import robertli.zero.entity.FileRecord;

/**
 *
 * @author Robert Li
 */
@Component("fileRecordDao")
public class FileRecordDaoImpl extends GenericHibernateDao<FileRecord, String> implements FileRecordDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public FileRecord saveFileRecord(String name, String type) {
        final String uuid = UUID.randomUUID().toString();
        final Date now = new Date();
        FileRecord fileRecord = new FileRecord();
        fileRecord.setUuid(uuid);
        fileRecord.setName(name);
        fileRecord.setType(type);
        fileRecord.setCreatedDate(now);
        fileRecord.setLastAccessDate(now);
        save(fileRecord);
        return fileRecord;
    }

    @Override
    public List<FileRecord> listRemovedFile() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from FileRecord where removed=true").list();
    }

}

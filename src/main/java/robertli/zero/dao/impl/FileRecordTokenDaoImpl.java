/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.dao.FileRecordTokenDao;
import robertli.zero.entity.FileRecordToken;

/**
 *
 * @version 2017-03-15 1.0.0
 * @author Robert Li
 */
@Component("fileRecordTokenDao")
public class FileRecordTokenDaoImpl extends GenericHibernateDao<FileRecordToken, String> implements FileRecordTokenDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public FileRecordToken saveFileRecord(String name, String type, long length) {
        final String uuid = UUID.randomUUID().toString();
        final Date now = new Date();
        FileRecordToken fileRecordToken = new FileRecordToken();
        fileRecordToken.setUuid(uuid);
        fileRecordToken.setName(name);
        fileRecordToken.setType(type);
        fileRecordToken.setCreatedDate(now);
        fileRecordToken.setLen(length);
        fileRecordToken.setFileRecord(null);
        save(fileRecordToken);
        return fileRecordToken;
    }

    @Override
    public List<FileRecordToken> listOverdueFileRecord(int lifeMinute) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -lifeMinute);
        Date endDate = cal.getTime();
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select t from FileRecordToken t where t.createdDate<=:endDate and t.fileRecord is null");
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

}

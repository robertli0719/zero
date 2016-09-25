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
package robertli.zero.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.hibernate.Query;
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
        fileRecord.setTemp(true);
        fileRecord.setCreatedDate(now);
        fileRecord.setLastAccessDate(now);
        save(fileRecord);
        return fileRecord;
    }

    @Override
    public List<FileRecord> listOverdueFileRecord(final int lifeMinute) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -lifeMinute);
        Date endDate = cal.getTime();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from FileRecord where createdDate<=:endDate and temp=true");
        query.setParameter("endDate", endDate);
        return query.list();
    }

}

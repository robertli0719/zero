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
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.dao.AdminOnlineDao;
import robertli.zero.entity.Admin;
import robertli.zero.entity.AdminOnline;

/**
 *
 * @author Robert Li
 */
@Component("adminOnlineDao")
public class AdminOnlineDaoImpl extends GenericHibernateDao<AdminOnline, String> implements AdminOnlineDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public void clear(final int lifeMinute) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -lifeMinute);
        Date endDate = cal.getTime();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from AdminOnline where lastActiveDate<=:endDate");
        query.setParameter("endDate", endDate);
        query.executeUpdate();
    }

    @Override
    public void saveAdminOnline(String sessionId, Admin admin) {
        AdminOnline adminOnline = new AdminOnline();
        adminOnline.setSessionId(sessionId);
        adminOnline.setAdmin(admin);
        adminOnline.setLastActiveDate(new Date());
        save(adminOnline);
    }

    @Override
    public void kickAdminByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from AdminOnline where admin=:username");
        query.setString("username", username);
        query.executeUpdate();
    }

}

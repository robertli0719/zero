/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
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

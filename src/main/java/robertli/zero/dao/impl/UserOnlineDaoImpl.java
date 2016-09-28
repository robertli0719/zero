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
import robertli.zero.dao.UserOnlineDao;
import robertli.zero.entity.User;
import robertli.zero.entity.UserOnline;
import robertli.zero.model.SearchResult;

@Component("userOnlineDao")
public class UserOnlineDaoImpl extends GenericHibernateDao<UserOnline, String> implements UserOnlineDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public void clear(final int lifeMinute) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -lifeMinute);
        Date endDate = cal.getTime();
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from UserOnline where lastActiveDate<=:endDate");
        query.setParameter("endDate", endDate);
        query.executeUpdate();
    }

    @Override
    public void saveUserOnline(String sessionId, User user) {
        UserOnline userOnline = new UserOnline();
        userOnline.setSessionId(sessionId);
        userOnline.setUser(user);
        userOnline.setLastActiveDate(new Date());
        save(userOnline);
    }

    @Override
    public SearchResult<UserOnline> paging(int pageId, int max) {
        return query("from UserOnline", pageId, max);
    }

}

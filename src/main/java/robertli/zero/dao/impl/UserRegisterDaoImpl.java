/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.dao.UserRegisterDao;
import robertli.zero.entity.UserRegister;

/**
 *
 * @author Robert Li
 */
@Component("userRegisterDao")
public class UserRegisterDaoImpl extends GenericHibernateDao<UserRegister, String> implements UserRegisterDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public void clear(int lifeMinute) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -lifeMinute);
        Date endDate = cal.getTime();

        Session session = sessionFactory.getCurrentSession();
        TypedQuery<UserRegister> query = session.createQuery("delete from UserRegister where signDate<=:endDate");
        query.setParameter("endDate", endDate);
        query.executeUpdate();
    }

    @Override
    public UserRegister getByVerifiedCode(String code) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<UserRegister> query = session.createQuery("from UserRegister where verifiedCode=:verifiedCode");
        query.setMaxResults(1);
        query.setParameter("verifiedCode", code);
        return (UserRegister) query.getSingleResult();
    }

}

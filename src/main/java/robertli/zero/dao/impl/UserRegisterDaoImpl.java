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
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -lifeMinute);
        final Date endDate = cal.getTime();

        final Session session = sessionFactory.getCurrentSession();
        final TypedQuery<UserRegister> query = session.createQuery("delete from UserRegister where signDate<=:endDate");
        query.setParameter("endDate", endDate);
        query.executeUpdate();
    }

    @Override
    public UserRegister getByVerifiedCode(String code) {
        final Session session = sessionFactory.getCurrentSession();
        final TypedQuery<UserRegister> query = session.createQuery("select r from UserRegister r where r.verifiedCode=:verifiedCode");
        query.setMaxResults(1);
        query.setParameter("verifiedCode", code);
        return (UserRegister) query.getSingleResult();
    }

    @Override
    public boolean isExistVerifiedCode(String code) {
        final Session session = sessionFactory.getCurrentSession();
        final TypedQuery<Long> query = session.createQuery("select count(r) from UserRegister r where r.verifiedCode=:verifiedCode");
        query.setMaxResults(1);
        query.setParameter("verifiedCode", code);
        final Long num = query.getSingleResult();
        return num.intValue() != 0;
    }

}

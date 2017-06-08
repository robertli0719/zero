/*
 * Copyright 2017 Robert Li.
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
import robertli.zero.dao.UserMobileBindingTokenDao;
import robertli.zero.entity.UserMobileBindingToken;

/**
 *
 * @author Robert Li
 */
@Component("userMobileBindingTokenDao")
public class UserMobileBindingTokenDaoImpl extends GenericHibernateDao<UserMobileBindingToken, Integer> implements UserMobileBindingTokenDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public void clear(int lifeMinute) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -lifeMinute);
        Date endDate = cal.getTime();
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("delete from UserMobileBindingToken where createDate<=:endDate");
        query.setParameter("endDate", endDate);
        query.executeUpdate();
    }

    private TypedQuery<UserMobileBindingToken> makeSingleResultQuery(String phoneNumber, String code) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select t from UserMobileBindingToken t where t.phoneNumber=:phoneNumber and t.code=:code");
        query.setParameter("phoneNumber", phoneNumber);
        query.setParameter("code", code);
        query.setMaxResults(1);
        return query;
    }

    @Override
    public boolean isExist(String phoneNumber, String code) {
        final TypedQuery<UserMobileBindingToken> query = makeSingleResultQuery(phoneNumber, code);
        return query.getResultList().isEmpty() == false;
    }

    @Override
    public UserMobileBindingToken get(String phoneNumber, String code) {
        final TypedQuery<UserMobileBindingToken> query = makeSingleResultQuery(phoneNumber, code);
        return query.getSingleResult();
    }

}

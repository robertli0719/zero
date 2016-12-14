/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import javax.annotation.Resource;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.dao.AccessTokenDao;
import robertli.zero.entity.AccessToken;

/**
 *
 * @author Robert Li
 */
@Component("accessTokenDao")
public class AccessTokenDaoImpl extends GenericHibernateDao<AccessToken, String> implements AccessTokenDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public void clear() {
        Session session = sessionFactory.getCurrentSession();

        TypedQuery<AccessToken> query = session.createQuery("delete from AccessToken where expiryDate<now()");
        query.executeUpdate();
    }

}

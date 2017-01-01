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
import robertli.zero.dao.UserDao;
import robertli.zero.dao.UserRoleDao;
import robertli.zero.dao.UserRoleItemDao;
import robertli.zero.entity.User;
import robertli.zero.entity.UserRole;
import robertli.zero.entity.UserRoleItem;

/**
 *
 * @author Robert Li
 */
@Component("userRoleItemDao")
public class UserRoleItemDaoImpl extends GenericHibernateDao<UserRoleItem, Integer> implements UserRoleItemDao {

    @Resource
    private SessionFactory sessionFactory;

    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Override
    public boolean isExist(int userId, String userRoleName) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Long> query;

        query = session.createQuery("select count(item) from UserRoleItem item where item.user.id=:userId and item.userRole.name=:userRoleName");
        query.setParameter("userId", userId);
        query.setParameter("userRoleName", userRoleName);
        return query.getSingleResult() > 0;
    }

    @Override
    public void put(int userId, String userRoleName) {
        if (isExist(userId, userRoleName)) {
            return;
        }
        User user = userDao.get(userId);
        UserRole userRole = userRoleDao.get(userRoleName);

        UserRoleItem item = new UserRoleItem();
        item.setUser(user);
        item.setUserRole(userRole);

        save(item);
    }

    @Override
    public void remove(int userId, String userRoleName) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Integer> query;
        query = session.createQuery("delete from UserRoleItem item where item.user.id=:userId and item.userRole.name=:userRoleName");
        query.setParameter("userId", userId);
        query.setParameter("userRoleName", userRoleName);
        query.executeUpdate();
    }

}

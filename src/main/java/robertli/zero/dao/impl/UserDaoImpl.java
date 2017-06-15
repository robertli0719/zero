/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.dao.UserDao;
import robertli.zero.entity.User;

/**
 *
 * @author Robert Li
 */
@Component("userDao")
public class UserDaoImpl extends GenericHibernateDao<User, Integer> implements UserDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public boolean isExistUid(String uid) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select u from User u where u.uid = :uid");
        query.setParameter("uid", uid);
        query.setMaxResults(1);
        return query.getResultList().isEmpty() == false;
    }

    @Override
    public User saveUser(String name, String password, String passwordSalt) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setPasswordSalt(passwordSalt);
        save(user);
        return user;
    }

    @Override
    public User getUserByUid(String uid) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery("select u from User u where u.uid = :uid");
        query.setParameter("uid", uid);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    public int countUserByPlatform(String userPlatformName) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Long> query = session.createQuery("select count(u) from User u where u.userPlatform.name= :userPlatformName", Long.class);
        query.setParameter("userPlatformName", userPlatformName);
        return query.getSingleResult().intValue();
    }

    @Override
    public int countUserByRole(String userRoleName) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Long> query = session.createQuery("select count(uri.user) from UserRoleItem uri where uri.userRole.name=:userRoleName group by uri.user.id", Long.class);
        query.setParameter("userRoleName", userRoleName);
        return query.getSingleResult().intValue();
    }

    @Override
    public int countSearch(String userPlatformName, String words) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Long> query = session.createQuery("select count(distinct ua.user.id) from UserAuth ua where ua.user.userPlatform.name = :userPlatformName and (ua.label like :words or ua.user.name like :words)", Long.class);
        query.setParameter("userPlatformName", userPlatformName);
        query.setParameter("words", "%" + words + "%");
        return query.getSingleResult().intValue();
    }

    @Override
    public List<User> getUserListByPlatform(String userPlatformName, int offset, int limit) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery("select u from User u where u.userPlatform.name= :userPlatformName", User.class);
        query.setParameter("userPlatformName", userPlatformName);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<User> getUserListByRole(String userRoleName, int offset, int limit) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery("select uri.user from UserRoleItem uri where uri.userRole.name=:userRoleName group by uri.user.id", User.class);
        query.setParameter("userRoleName", userRoleName);
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    @Override
    public List<User> search(String userPlatformName, String words, int offset, int limit) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<User> query = session.createQuery("select ua.user from UserAuth ua where ua.user.userPlatform.name = :userPlatformName and (ua.label like :words or ua.user.name like :words) group by ua.user.id", User.class);
        query.setParameter("userPlatformName", userPlatformName);
        query.setParameter("words", "%" + words + "%");
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

}

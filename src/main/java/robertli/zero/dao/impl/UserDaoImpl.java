/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.dao.impl;

import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;
import robertli.zero.dao.UserDao;
import robertli.zero.entity.User;

/**
 *
 * @author Robert Li
 */
@Component("userDao")
public class UserDaoImpl implements UserDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public void save(User user) {               
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        //session.close();
    }

}

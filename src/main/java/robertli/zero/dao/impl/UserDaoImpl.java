/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.dao.UserDao;
import robertli.zero.entity.User;
import robertli.zero.model.SearchResult;

/**
 *
 * @author Robert Li
 */
@Component("userDao")
public class UserDaoImpl extends GenericHibernateDao<User, Integer> implements UserDao {

    @Resource
    private SessionFactory sessionFactory;

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
    public SearchResult<User> paging(int pageId, int max) {
        SearchResult<User> result = query("from User", pageId, max);
        for (User user : result.getList()) {
            user.getUserAuthList().size();//fetch UserAuth
        }
        return result;
    }

}

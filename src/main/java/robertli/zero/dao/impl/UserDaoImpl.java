/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.UserDao;
import robertli.zero.entity.User;

/**
 *
 * @author Robert Li
 */
@Component("userDao")
public class UserDaoImpl extends GenericHibernateDao<User, Integer> implements UserDao {

    @Override
    public User saveUser(String name, String password, String passwordSalt) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setPasswordSalt(passwordSalt);
        save(user);
        return user;
    }

}

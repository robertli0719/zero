/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import robertli.zero.entity.User;

/**
 *
 * @author Robert Li
 */
public interface UserDao extends GenericDao<User, Integer> {

    public User saveUser(String name, String password, String passwordSalt);
}

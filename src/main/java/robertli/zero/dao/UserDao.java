/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import java.util.List;
import robertli.zero.entity.User;

/**
 *
 * @version 1.0.2 2017-03-28
 * @author Robert Li
 */
public interface UserDao extends GenericDao<User, Integer> {

    public User saveUser(String name, String password, String passwordSalt);

    public User getUserByUid(String uid);

    public int countUserByPlatform(String userPlatformName);

    public int countUserByRole(String userRoleName);

    public List<User> getUserListByPlatform(String userPlatformName, int offset, int limit);

    public List<User> getUserListByRole(String userRoleName, int offset, int limit);

}

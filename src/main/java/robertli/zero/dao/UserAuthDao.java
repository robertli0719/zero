/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;

/**
 *
 * @author Robert Li
 */
public interface UserAuthDao extends GenericDao<UserAuth, String> {

    public UserAuth saveUserAuth(String userType, String clientId, String username, String label, String usernameType, User user);
}

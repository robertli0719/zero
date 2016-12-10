/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.List;
import robertli.zero.entity.User;

/**
 * This service is design for administrators to manage user data.
 *
 * @version 1.0.1 2016-12-09
 * @author Robert Li
 */
public interface UserManagementService {

    public void addUser(String type, String clientId, String usernameType, String username, String password, String nickname);

    public void setLock(int userId, boolean lock);

    public List<User> getUserList();

    public List<User> getUserListByType(String type);

}

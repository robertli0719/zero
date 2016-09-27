/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.List;
import robertli.zero.entity.User;
import robertli.zero.entity.UserOnline;

/**
 * This service is design for administrators to manage user data.
 *
 * @version 1.0 2016-09-27
 * @author Robert Li
 */
public interface UserManagementService {

    /**
     * get the total number of users;
     *
     * @return the size of user list
     */
    public int countUsers();

    /**
     * This function should fetch UserAuth together
     *
     * @param start the first line to fetch data.
     * @param max the max lines to fetch
     * @return the list of users
     */
    public List<User> getUserList(int start, int max);

    /**
     * get the total number of online users;
     *
     * @return the size of user list
     */
    public int countOnlineUsers();

    /**
     * This function should get User together
     *
     * @param start the first line to fetch data.
     * @param max the max lines to fetch
     * @return the list of online users
     */
    public List<UserOnline> getUserOnlineList(int start, int max);
}

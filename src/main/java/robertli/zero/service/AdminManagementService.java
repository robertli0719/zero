/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.List;
import robertli.zero.entity.User;

/**
 * This service is design for super administrators such as root user to manage
 * other administrators. super administrators can use this service to add,
 * delete, suspend, reset password, and and query any needed information for
 * other administrators.
 *
 * @see AdminService
 * @version 1.0 2016-09-21
 * @author Robert Li
 */
public interface AdminManagementService {

    /**
     * add a new administrator to the system
     *
     * @param sessionId the identification of a web browser session
     * @param username the username of new administrator
     * @param orginealPassword the initial password for the new administrator
     * @return true if fail
     */
    public boolean addAdmin(String sessionId, String username, String orginealPassword);

    /**
     * delete an administrator from the system
     *
     * @param sessionId the identification of a web browser session
     * @param username the username of the administrator who will be delete
     * @return true if fail
     */
    public boolean deleteAdmin(String sessionId, String username);

    /**
     * the administrator who is suspended can't login the system
     *
     * @param sessionId the identification of a web browser session
     * @param username the username for the administrator who will be processed
     * @param suspended true if we want the administrator should be suspended
     * @return true if fail
     */
    public boolean setSuspendStatus(String sessionId, String username, boolean suspended);

    /**
     * Super administrator use this function to reset the password of other
     * administrator.
     *
     * @param sessionId the identification of a web browser session
     * @param username the username for the administrator who will be processed
     * @param newPassword the new password for the administrator
     * @return true if fail
     */
    public boolean resetPassword(String sessionId, String username, String newPassword);

    /**
     * List all the administrator
     *
     * @param sessionId the identification of a web browser session
     * @return List of administrator
     */
    public List<User> getAdminList(String sessionId);
}

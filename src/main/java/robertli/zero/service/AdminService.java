/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import robertli.zero.entity.User;

/**
 * This service is design for the administrators to login, logout, query
 * information, and change information for themselves.The system need to get the
 * sessionId from cookie or device to identify a web browser or device session.
 * For each sessionId, there should be no more than one administrator keep in
 * login status at the same time, but one administrator can use different
 * sessionIds to keep in login status at the same time. After login, the
 * administrator need to use getCurrentAdmin to keep the session effective
 * within 60 minutes.
 *
 * @see AdminManagementService
 * @version 1.1 2016-12-08
 * @author Robert Li
 */
public interface AdminService {

    /**
     * insert default Admin record for first using.
     */
    public void init();

    /**
     * Get the current login administrator
     *
     * @param sessionId the identification of a web browser session
     * @return return the Admin entity<br> return null if there is no
     * administrator login.
     */
    public User getCurrentAdmin(String sessionId);

    public static enum AdminLoginResult {
        SUCCESS,
        PASSWORD_WRONG,
        SUSPENDED_STATUS,
        ADMIN_LOGINED,
        DATABASE_EXCEPTION
    }

    /**
     * Administrator login the system using their own username and password
     *
     * @param sessionId the identification of a web browser session
     * @param username the username of the administrator
     * @param orginealPassword the input of password field.
     * @return the result set of running.
     */
    public AdminLoginResult login(String sessionId, String username, String orginealPassword);

    /**
     * Login administrators use this function to logout the system.
     *
     * @param sessionId the identification of a web browser session.
     * @return true when running fail
     */
    public boolean logout(String sessionId);

    public static enum AdminResetPasswordResult {
        NEED_LOGIN,
        OLD_PASSWORD_WRONG,
        NEW_PASSWORD_IS_TOO_EASY,
        PASSWORD_AGAIN_ERROR,
        SUCCESS,
        FAIL
    }

    /**
     * administrators reset password for themselves
     *
     * @param sessionId the identification of a web browser session
     * @param oldPassword the old password for the administrators
     * @param newPassword the new password for the administrators
     * @param newPasswordAgain type again the new password
     * @return AdminResetPasswordResult
     */
    public AdminResetPasswordResult resetPassword(String sessionId, String oldPassword, String newPassword, String newPasswordAgain);

}

/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import robertli.zero.entity.User;

/**
 * This service is design for login, logout and query information of users<br>
 * The system need to get the sessionId from cookie or device to identify a web
 * browser or device session. For each sessionId, there should be no more than
 * one user keep in login status at the same time, but one user can use
 * different sessionIds to keep in login status at the same time. After login,
 * the user need to use getCurrentUser to keep the session effective within 60
 * minutes.
 *
 * @version 1.01 2016-09-19
 * @author Robert Li
 */
public interface UserService {

    /**
     * Get the current login user
     *
     * @param sessionId the identification of a web browser session
     * @return return the User entity<br> return null if there is no user login.
     */
    public User getCurrentUser(String sessionId);

    public static enum UserLoginResult {
        SUCCESS,
        PASSWORD_WRONG,
        USER_LOGINED,
        DATABASE_EXCEPTION
    }

    /**
     * Users login the system using their own email or telephone or any other
     * authId with password
     *
     * @param sessionId the identification of a web browser session.
     * @param userAuthId the input of the first form field, such as email.
     * @param orginealPassword the input of password field.
     * @return the result set of running.
     */
    public UserLoginResult login(String sessionId, String userAuthId, String orginealPassword);

    public enum UserLoginByGoogleResult {
        SUCCESS,
        USER_LOGINED,
        NO_VERIFIED_EMAIL,
        FAIL
    }

    /**
     * user can login directly by Google account: <br />
     * https://developers.google.com/identity/sign-in/web/backend-auth
     * <br />
     * Only the users who have verified email within their Google account can
     * use Google to login our system. (I think almost all the Google account
     * have verified email). If the email has registered in our system before,
     * we just login using this email. If the email never register before, the
     * system will help he or she to uses this email to register automatically
     * with a random password and login using the email.
     *
     * @param sessionId the identification of a web browser session.
     * @param token an ID token get from Google identity.
     * @return UserLoginByGoogleResult
     */
    public UserLoginByGoogleResult loginByGoogle(String sessionId, String token);

    /**
     * Login users use this function to logout the system.
     *
     * @param sessionId the identification of a web browser session.
     * @return true when running fail
     */
    public boolean logout(String sessionId);

}

/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import robertli.zero.dto.user.UserAuthDto;
import robertli.zero.dto.user.UserProfileDto;

/**
 * The service for users to operate themselves
 *
 * @version 1.0 2016-12-09
 * @author Robert Li
 */
public interface UserService {

    public static final String USER_TYPE_GENERAL = "general";
    public static final String USER_TYPE_STAFF = "staff";
    public static final String USER_TYPE_ADMIN = "admin";

    /**
     * The function is for current online user to get info for self.<br>
     * If the user has not logged in, this function should return a
     * userProfileDto with authLabel=null
     *
     * @param token the access_token
     * @return UserProfileDto and never be null
     */
    public UserProfileDto getUserProfile(String token);

    /**
     * For user login
     *
     * @param userAuthDto user login form
     * @return access_token
     */
    public String putAuth(UserAuthDto userAuthDto);

    /**
     * For user logout
     *
     * @param token access_token
     */
    public void deleteAuth(String token);

    // add google login in the future
    /*
      user can login directly by Google account:
      https://developers.google.com/identity/sign-in/web/backend-auth
      
      Only the users who have verified email within their Google account can
      use Google to login our system. (I think almost all the Google account
      have verified email). If the email has registered in our system before,
      we just login using this email. If the email never register before, the
      system will help he or she to uses this email to register automatically
      with a random password and login using the email.
     */
    // add facebook login in the future
}

/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import robertli.zero.dto.QueryResult;
import robertli.zero.dto.user.UserPlatformDto;
import robertli.zero.dto.user.UserRoleDto;
import robertli.zero.dto.user.UserTypeDto;
import robertli.zero.entity.User;

/**
 * This service is design to manage user data.<br>
 *
 * Don't use this service in controller layer. This service is used for other
 * services.
 *
 * @version 1.0.7 2017-07-31
 * @author Robert Li
 */
public interface UserService {

    public static final String USER_TYPE_GENERAL = "general";
    public static final String USER_TYPE_MERCHANT = "merchant";
    public static final String USER_TYPE_STAFF = "staff";
    public static final String USER_TYPE_ADMIN = "admin";

    public static final String USER_PLATFORM_GENERAL = "general";
    public static final String USER_PLATFORM_MERCHANT = "merchant";
    public static final String USER_PLATFORM_ADMIN = "admin";

    public static final String USERNAME_TYPE_STRING = "string";
    public static final String USERNAME_TYPE_EMAIL = "email";
    public static final String USERNAME_TYPE_TELEPHONE = "telephone";

    public static final String USER_ROLE_ADMIN_ROOT = "admin_root";
    public static final String USER_ROLE_PLATFORM_ROOT = "platform_root";

    public void addUserType(String name);

    public void deleteUserType(String name);

    public void addUserPlatform(String userTypeName, String name);

    public void putUserPlatform(String userTypeName, String name);

    public void deleteUserPlatform(String name);

    public void addUserRole(String name);

    public void deleteUserRole(String name);

    public User getUser(String userPlatformName, String username);

    public User addUser(String userPlatformName, String username,
            String usernameType, String label, String password, String passwordSalt,
            String name, boolean locked);

    public User addUser(String userPlatformName, String username,
            String usernameType, String label, String originalPassword,
            String name, boolean locked);

    public void deleteUser(String userPlatformName, String username);

    public void resetPassword(String userPlatformName, String username, String originalPassword);

    public void putRoleForUser(int userId, String roleName);

    public void deleteRoleForUser(int userId, String roleName);

    public void setLock(int userId, boolean locked);

    public QueryResult<UserTypeDto> getUserTypeList(int offset, int limit);

    public QueryResult<User> getUserListByPlatform(String userPlatformName, int offset, int limit);

    public QueryResult<User> getUserListByRole(String roleName, int offset, int limit);

    public QueryResult<UserPlatformDto> getUserPlatformList(int offset, int limit);

    public QueryResult<UserRoleDto> getUserRoleList(int offset, int limit);

}

/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.List;
import robertli.zero.dto.user.UserDto;
import robertli.zero.dto.user.UserPlatformDto;
import robertli.zero.dto.user.UserTypeDto;

/**
 * This service is design for administrators to manage user data.
 *
 * @version 1.0.2 2016-12-29
 * @author Robert Li
 */
public interface UserManagementService {

    public void addUserType(String name);

    public void deleteUserType(String name);

    public void addUserPlatform(String userTypeName, String name);

    public void deleteUserPlatform(String name);

    public void addUser(UserDto userDto);

    public void deleteUser(int userId);

    public void updateUser(UserDto userDto);

    public void setLock(int userId, boolean locked);

    public List<UserTypeDto> getUserTypeList();

    public List<UserPlatformDto> getUserPlatformList();

    public List<UserDto> getUserList();

}

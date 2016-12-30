/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.AppConfiguration;
import robertli.zero.dao.UserTypeDao;
import robertli.zero.dto.user.UserDto;
import robertli.zero.service.AppService;
import robertli.zero.service.UserManagementService;
import robertli.zero.service.UserService;

@Component("appService")
public class AppServiceImpl implements AppService {

    @Resource
    private UserManagementService userManagementService;

    @Resource
    private UserTypeDao userTypeDao;

    @Resource
    private AppConfiguration appConfiguration;

    @Override
    public void init() {
        if (userTypeDao.list().isEmpty() == false) {
            return;
        }

        String initAdminName = appConfiguration.getInitAdminName();
        String initAdminPassword = appConfiguration.getInitAdminPassword();

        userManagementService.addUserType(UserService.USER_TYPE_GENERAL);
        userManagementService.addUserType(UserService.USER_TYPE_ADMIN);
        userManagementService.addUserType(UserService.USER_TYPE_STAFF);

        userManagementService.addUserPlatform(UserService.USER_TYPE_GENERAL, UserService.USER_PLATFORM_GENERAL);
        userManagementService.addUserPlatform(UserService.USER_TYPE_ADMIN, UserService.USER_PLATFORM_ADMIN);

        UserDto userDto = new UserDto();
        userDto.setLabel(initAdminName);
        userDto.setLocked(false);
        userDto.setName(initAdminName);
        userDto.setPassword(initAdminPassword);
        userDto.setTelephone(null);
        userDto.setUserPlatformName(UserService.USER_PLATFORM_ADMIN);
        userDto.setUsername(initAdminName);
        userDto.setUsernameType(UserService.USERNAME_TYPE_STRING);

        userManagementService.addUser(userDto);
    }

}

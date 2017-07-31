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
import robertli.zero.dto.user.AdminUserDto;
import robertli.zero.service.AdminUserService;
import robertli.zero.service.AppService;
import robertli.zero.service.UserService;

@Component("appService")
public class AppServiceImpl implements AppService {

    @Resource
    private UserService userService;

    @Resource
    private AdminUserService adminUserService;

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

        userService.addUserType(UserService.USER_TYPE_GENERAL);
        userService.addUserType(UserService.USER_TYPE_ADMIN);
        userService.addUserType(UserService.USER_TYPE_STAFF);
        userService.addUserType(UserService.USER_TYPE_MERCHANT);

        userService.addUserPlatform(UserService.USER_TYPE_GENERAL, UserService.USER_PLATFORM_GENERAL);
        userService.addUserPlatform(UserService.USER_TYPE_ADMIN, UserService.USER_PLATFORM_ADMIN);
        userService.addUserPlatform(UserService.USER_TYPE_MERCHANT, UserService.USER_PLATFORM_MERCHANT);

        userService.addUserRole(UserService.USER_ROLE_ADMIN_ROOT);
        userService.addUserRole(UserService.USER_ROLE_PLATFORM_ROOT);

        AdminUserDto adminUserDto = new AdminUserDto();

        adminUserDto.setUsername(initAdminName);
        adminUserDto.setPassword(initAdminPassword);
        adminUserService.addAdminUser(adminUserDto);
        adminUserService.addRootRole(initAdminName);
    }

}

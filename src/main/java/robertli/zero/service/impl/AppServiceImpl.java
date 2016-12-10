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
import robertli.zero.entity.UserType;
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

    private UserType addUserType(String type) {
        UserType userType = new UserType();
        userType.setName(type);
        userTypeDao.save(userType);
        return userType;
    }

    @Override
    public void init() {
        if (userTypeDao.list().isEmpty() == false) {
            return;
        }

        String initAdminName = appConfiguration.getInitAdminName();
        String initAdminPassword = appConfiguration.getInitAdminPassword();

        addUserType(UserService.USER_TYPE_GENERAL);
        addUserType(UserService.USER_TYPE_STAFF);
        addUserType(UserService.USER_TYPE_ADMIN);
        userManagementService.addUser(UserService.USER_TYPE_ADMIN, null, "string", initAdminName, initAdminPassword, initAdminName);
    }

}

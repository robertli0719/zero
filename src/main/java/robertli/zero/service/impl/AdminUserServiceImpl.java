/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import robertli.zero.core.SecurityService;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dao.UserDao;
import robertli.zero.dao.UserPlatformDao;
import robertli.zero.dto.user.AdminUserDto;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;
import robertli.zero.entity.UserPlatform;
import robertli.zero.entity.UserRoleItem;
import robertli.zero.service.AdminUserService;
import robertli.zero.service.UserService;

@Component("adminUserService")
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private SecurityService securityService;

    @Resource
    private UserPlatformDao userPlatformDao;

    @Resource
    private UserDao userDao;

    @Resource
    private UserAuthDao userAuthDao;

    @Override
    public List<AdminUserDto> getAdminUserList() {
        UserPlatform userPlatform = userPlatformDao.get(UserService.USER_PLATFORM_ADMIN);
        List<User> userList = userPlatform.getUserList();
        return modelMapper.map(userList, new TypeToken<List<AdminUserDto>>() {
        }.getType());
    }

    @Override
    public boolean isAdminUser(int userId) {
        User user = userDao.get(userId);
        if (user == null) {
            return false;
        }
        UserPlatform userPlatform = user.getUserPlatform();
        if (userPlatform == null || userPlatform.getName() == null) {
            return false;
        }
        return userPlatform.getName().equals(UserService.USER_PLATFORM_ADMIN);
    }

    @Override
    public boolean isRoot(int userId) {
        if (isAdminUser(userId) == false) {
            return false;
        }
        User user = userDao.get(userId);
        for (UserRoleItem roleItem : user.getUserRoleItemList()) {
            String roleName = roleItem.getUserRole().getName();
            if (roleName.equals(UserService.USER_ROLE_ADMIN_ROOT)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addAdminUser(AdminUserDto adminUserDto) {
        String orginealPassword = adminUserDto.getPassword();
        String username = adminUserDto.getUsername();
        boolean locked = adminUserDto.isLocked();

        String userPlatformName = UserService.USER_PLATFORM_ADMIN;
        String usernameType = UserService.USERNAME_TYPE_STRING;
        String name = username;
        String label = username;
        String salt = securityService.createPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);

        UserPlatform userPlatform = userPlatformDao.get(userPlatformName);
        User adminUser = new User();
        adminUser.setName(name);
        adminUser.setPassword(password);
        adminUser.setPasswordSalt(salt);
        adminUser.setUserPlatform(userPlatform);
        adminUser.setLocked(locked);
        userDao.save(adminUser);

        userAuthDao.saveUserAuth(userPlatformName, username, label, usernameType, adminUser);
    }

    @Override
    public void deleteAdminUser(int userId) {
        if (isAdminUser(userId) == false) {
            return;
        }
        User user = userDao.get(userId);
        for (UserAuth userAuth : user.getUserAuthList()) {
            userAuthDao.delete(userAuth);
        }
        userDao.delete(user);
    }

    @Override
    public void addRootRole(int userId) {
        if (isAdminUser(userId) == false) {
            throw new RuntimeException("this user is not adminUser");
        }
        
    }

    @Override
    public void removeRootRole(int userId) {
        if (isAdminUser(userId) == false) {
            throw new RuntimeException("this user is not adminUser");
        }
    }

    @Override
    public void resetPassword(int userId, String orginealPassword) {
        if (isAdminUser(userId) == false) {
            throw new RuntimeException("this user is not adminUser");
        }
        String salt = securityService.createPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);
        User user = userDao.get(userId);
        user.setPassword(password);
        user.setPasswordSalt(salt);
    }

    @Override
    public void lockAdminUser(int userId) {
        if (isAdminUser(userId) == false) {
            return;
        }
        User user = userDao.get(userId);
        user.setLocked(true);
    }

    @Override
    public void unlockAdminUser(int userId) {
        if (isAdminUser(userId) == false) {
            return;
        }
        User user = userDao.get(userId);
        user.setLocked(false);
    }

}

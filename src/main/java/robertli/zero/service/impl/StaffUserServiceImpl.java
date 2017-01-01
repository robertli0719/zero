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
import robertli.zero.dao.UserRoleItemDao;
import robertli.zero.dto.user.StaffUserDto;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;
import robertli.zero.entity.UserPlatform;
import robertli.zero.entity.UserRoleItem;
import robertli.zero.service.StaffUserService;
import robertli.zero.service.UserService;

@Component("staffUserService")
public class StaffUserServiceImpl implements StaffUserService {

    @Resource
    private ModelMapper modelMapper;

    @Resource
    private SecurityService securityService;

    @Resource
    private UserPlatformDao userPlatformDao;

    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleItemDao userRoleItemDao;

    @Resource
    private UserAuthDao userAuthDao;

    @Override
    public List<StaffUserDto> getStaffUserList(final String userPlatformName) {
        UserPlatform userPlatform = userPlatformDao.get(userPlatformName);
        List<User> userList = userPlatform.getUserList();
        return modelMapper.map(userList, new TypeToken<List<StaffUserDto>>() {
        }.getType());
    }

    @Override
    public boolean isStaffUser(final String userPlatformName, int userId) {
        User user = userDao.get(userId);
        if (user == null) {
            return false;
        }
        UserPlatform userPlatform = user.getUserPlatform();
        if (userPlatform == null || userPlatform.getName() == null) {
            return false;
        }
        return userPlatform.getName().equals(userPlatformName);
    }

    @Override
    public boolean isPlatformRoot(final String userPlatformName, int userId) {
        if (isStaffUser(userPlatformName, userId) == false) {
            return false;
        }
        return userRoleItemDao.isExist(userId, UserService.USER_ROLE_PLATFORM_ROOT);
    }

    @Override
    public void addStaffUser(final StaffUserDto staffUserDto) {
        String orginealPassword = staffUserDto.getPassword();
        String username = staffUserDto.getUsername();
        boolean locked = staffUserDto.isLocked();
        String userPlatformName = staffUserDto.getUserPlatformName();

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
    public void deleteStaffUser(final String userPlatformName, final int userId) {
        if (isStaffUser(userPlatformName, userId) == false) {
            throw new RuntimeException("no this staff in this platform");
        }
        User user = userDao.get(userId);
        for (UserAuth userAuth : user.getUserAuthList()) {
            userAuthDao.delete(userAuth);
        }
        for (UserRoleItem userRoleItem : user.getUserRoleItemList()) {
            userRoleItemDao.delete(userRoleItem);
        }
        userDao.delete(user);
    }

    @Override
    public void addPlatformRootRole(final String userPlatformName, final int userId) {
        if (isStaffUser(userPlatformName, userId) == false) {
            throw new RuntimeException("this user is not in this platform");
        }
        userRoleItemDao.put(userId, UserService.USER_ROLE_PLATFORM_ROOT);
    }

    @Override
    public void removePlatformRootRole(final String userPlatformName, final int userId) {
        if (isStaffUser(userPlatformName, userId) == false) {
            throw new RuntimeException("this user is not in this platform");
        }
        userRoleItemDao.remove(userId, UserService.USER_ROLE_PLATFORM_ROOT);
    }

    @Override
    public void resetPassword(final String userPlatformName, int userId, final String orginealPassword) {
        if (isStaffUser(userPlatformName, userId) == false) {
            throw new RuntimeException("this user is not in this platform");
        }
        String salt = securityService.createPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);
        User user = userDao.get(userId);
        user.setPassword(password);
        user.setPasswordSalt(salt);
    }

    @Override
    public void lockStaffUser(final String userPlatformName, final int userId) {
        if (isStaffUser(userPlatformName, userId) == false) {
            throw new RuntimeException("this user is not in this platform");
        }
        User user = userDao.get(userId);
        user.setLocked(true);
    }

    @Override
    public void unlockStaffUser(String userPlatformName, int userId) {
        if (isStaffUser(userPlatformName, userId) == false) {
            throw new RuntimeException("this user is not in this platform");
        }
        User user = userDao.get(userId);
        user.setLocked(false);
    }

}

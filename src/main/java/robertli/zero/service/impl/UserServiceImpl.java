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
import robertli.zero.dao.UserRoleDao;
import robertli.zero.dao.UserRoleItemDao;
import robertli.zero.dao.UserTypeDao;
import robertli.zero.dto.user.UserDto;
import robertli.zero.dto.user.UserPlatformDto;
import robertli.zero.dto.user.UserRoleDto;
import robertli.zero.dto.user.UserTypeDto;
import robertli.zero.entity.UserPlatform;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;
import robertli.zero.entity.UserRole;
import robertli.zero.entity.UserType;
import robertli.zero.service.UserService;

@Component("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private SecurityService securityService;

    @Resource
    private UserTypeDao userTypeDao;

    @Resource
    private UserPlatformDao userPlatformDao;

    @Resource
    private UserDao userDao;

    @Resource
    private UserAuthDao userAuthDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private UserRoleItemDao userRoleItemDao;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public void addUserType(String name) {
        UserType userType = new UserType();
        userType.setName(name);
        userTypeDao.save(userType);
    }

    @Override
    public void deleteUserType(String name) {
        userTypeDao.deleteById(name);
    }

    @Override
    public void addUserPlatform(String userTypeName, String name) {
        UserType userType = userTypeDao.get(userTypeName);
        UserPlatform userPlatform = new UserPlatform();
        userPlatform.setName(name);
        userPlatform.setUserType(userType);
        userPlatformDao.save(userPlatform);
    }

    @Override
    public void deleteUserPlatform(String name) {
        userPlatformDao.deleteById(name);
    }

    @Override
    public void addUserRole(String name) {
        UserRole userRole = new UserRole();
        userRole.setName(name);
        userRoleDao.save(userRole);
    }

    @Override
    public void deleteUserRole(String name) {
        userRoleDao.deleteById(name);
    }

    @Override
    public User getUser(String userPlatformName, String username) {
        UserPlatform userPlatform = userPlatformDao.get(userPlatformName);
        String userTypeName = userPlatform.getUserType().getName();
        String authId = userAuthDao.makeAuthId(userTypeName, userPlatformName, username);
        UserAuth userAuth = userAuthDao.get(authId);
        if (userAuth == null) {
            return null;
        }
        return userAuth.getUser();
    }

    @Override
    public User addUser(String userPlatformName, String username, String usernameType, String label, String orginealPassword, String name, String telephone, boolean locked) {
        String salt = securityService.createPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);

        UserPlatform userPlatform = userPlatformDao.get(userPlatformName);
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setPasswordSalt(salt);
        user.setUserPlatform(userPlatform);
        user.setLocked(locked);
        user.setTelephone(telephone);
        userDao.save(user);
        userAuthDao.saveUserAuth(userPlatformName, username, label, usernameType, user);
        return user;
    }

    @Override
    public void putRoleForUser(int userId, String roleName) {
        userRoleItemDao.put(userId, roleName);
    }

    @Override
    public void deleteRoleForUser(int userId, String roleName) {
        userRoleItemDao.remove(userId, roleName);
    }

    @Override
    public void setLock(int userId, boolean locked) {
        User user = userDao.get(userId);
        user.setLocked(locked);
    }

    @Override
    public List<UserTypeDto> getUserTypeList() {
        List<UserType> userTypeList = userTypeDao.list();
        return modelMapper.map(userTypeList, new TypeToken<List<UserTypeDto>>() {
        }.getType());
    }

    @Override
    public List<UserPlatformDto> getUserPlatformList() {
        List<UserPlatform> userPlatformList = userPlatformDao.list();
        return modelMapper.map(userPlatformList, new TypeToken<List<UserPlatformDto>>() {
        }.getType());
    }

    @Override
    public List<UserDto> getUserList() {
        List<User> userList = userDao.list();
        return modelMapper.map(userList, new TypeToken<List<UserDto>>() {
        }.getType());
    }

    @Override
    public List<UserRoleDto> getUserRoleList() {
        List<UserRole> userRoleList = userRoleDao.list();
        return modelMapper.map(userRoleList, new TypeToken<List<UserRoleDto>>() {
        }.getType());
    }

}

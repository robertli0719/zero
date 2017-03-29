/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import robertli.zero.core.AppConfiguration;
import robertli.zero.core.SecurityService;
import robertli.zero.dao.AccessTokenDao;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dao.UserDao;
import robertli.zero.dao.UserPlatformDao;
import robertli.zero.dao.UserRoleDao;
import robertli.zero.dao.UserRoleItemDao;
import robertli.zero.dao.UserTypeDao;
import robertli.zero.dto.QueryResult;
import robertli.zero.dto.user.UserPlatformDto;
import robertli.zero.dto.user.UserRoleDto;
import robertli.zero.dto.user.UserTypeDto;
import robertli.zero.entity.UserPlatform;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;
import robertli.zero.entity.UserRole;
import robertli.zero.entity.UserRoleItem;
import robertli.zero.entity.UserType;
import robertli.zero.service.UserService;

@Component("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private AppConfiguration appConfiguration;

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
    private AccessTokenDao accessTokenDao;

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
    public void deleteUser(String userPlatformName, String username) {
        User user = getUser(userPlatformName, username);
        for (UserAuth userAuth : user.getUserAuthList()) {
            userAuthDao.delete(userAuth);
        }
        for (UserRoleItem userRoleItem : user.getUserRoleItemList()) {
            userRoleItemDao.delete(userRoleItem);
        }
        accessTokenDao.deleteByUser(user.getId());
        userDao.delete(user);
    }

    @Override
    public void resetPassword(String userPlatformName, String username, String orginealPassword) {
        String salt = securityService.createPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);
        User user = getUser(userPlatformName, username);
        user.setPassword(password);
        user.setPasswordSalt(salt);
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

    private String generatUid(int userId) {
        long p = Integer.parseInt(appConfiguration.getUserIdSeedP());
        long q = Integer.parseInt(appConfiguration.getUserIdSeedQ());
        long tail = (userId / q) * q;
        long val = p * userId % q + tail;
        return "" + val;
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
        user.setUid(UUID.randomUUID().toString());
        userDao.save(user);
        String uid = generatUid(user.getId());
        user.setUid(uid);
        userAuthDao.saveUserAuth(userPlatformName, username, label, usernameType, user);
        return user;
    }

    @Override
    public void putRoleForUser(int userId, String roleName) {
        userRoleItemDao.put(userId, roleName);
    }

    @Override
    public void deleteRoleForUser(int userId, String roleName) {
        userRoleItemDao.delete(userId, roleName);
    }

    @Override
    public void setLock(int userId, boolean locked) {
        User user = userDao.get(userId);
        user.setLocked(locked);
    }

    @Override
    public QueryResult<UserTypeDto> getUserTypeList(int offset, int limit) {
        final List<UserType> userTypeList = userTypeDao.list(offset, limit);
        final List<UserTypeDto> resultList = modelMapper.map(userTypeList, new TypeToken<List<UserTypeDto>>() {
        }.getType());
        final int count = userTypeDao.count();
        return new QueryResult<>(offset, limit, count, resultList);
    }

    @Override
    public QueryResult<UserPlatformDto> getUserPlatformList(int offset, int limit) {
        final List<UserPlatform> userPlatformList = userPlatformDao.list(offset, limit);
        final List<UserPlatformDto> resultList = modelMapper.map(userPlatformList, new TypeToken<List<UserPlatformDto>>() {
        }.getType());
        final int count = userPlatformDao.count();
        return new QueryResult<>(offset, limit, count, resultList);
    }

    @Override
    public QueryResult<User> getUserListByPlatform(String userPlatformName, int offset, int limit) {
        final List<User> userList = userDao.getUserListByPlatform(userPlatformName, offset, limit);
        final int count = userDao.countUserByPlatform(userPlatformName);
        return new QueryResult<>(offset, limit, count, userList);
    }

    @Override
    public QueryResult<User> getUserListByRole(String roleName, int offset, int limit) {
        final List<User> userList = userDao.getUserListByRole(roleName, offset, limit);
        final int count = userDao.countUserByRole(roleName);
        return new QueryResult<>(offset, limit, count, userList);
    }

    @Override
    public QueryResult<UserRoleDto> getUserRoleList(int offset, int limit) {
        final List<UserRole> userRoleList = userRoleDao.list(offset, limit);
        final List<UserRoleDto> resultList = modelMapper.map(userRoleList, new TypeToken<List<UserRoleDto>>() {
        }.getType());
        final int count = userRoleDao.count();
        return new QueryResult<>(offset, limit, count, resultList);
    }

}

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
import robertli.zero.dao.UserTypeDao;
import robertli.zero.dto.user.UserDto;
import robertli.zero.dto.user.UserPlatformDto;
import robertli.zero.dto.user.UserTypeDto;
import robertli.zero.entity.UserPlatform;
import robertli.zero.entity.User;
import robertli.zero.entity.UserType;
import robertli.zero.service.UserManagementService;

@Component("userManagementService")
public class UserManagementServiceImpl implements UserManagementService {

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
    public void addUser(UserDto userDto) {
        String orginealPassword = userDto.getPassword();
        String userPlatformName = userDto.getUserPlatformName();
        String name = userDto.getName();
        String username = userDto.getUsername();
        String usernameType = userDto.getUsernameType();
        String label = userDto.getLabel();
        String salt = securityService.createPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);

        UserPlatform userPlatform = userPlatformDao.get(userPlatformName);
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setPasswordSalt(salt);
        user.setUserPlatform(userPlatform);
        userDao.save(user);

        userAuthDao.saveUserAuth(userPlatformName, username, label, usernameType, user);
    }

    @Override
    public void deleteUser(int userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateUser(UserDto userDto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

}

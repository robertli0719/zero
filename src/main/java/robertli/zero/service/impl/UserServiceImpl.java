/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import robertli.zero.controller.RestException;
import robertli.zero.dao.AccessTokenDao;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dao.UserDao;
import robertli.zero.dto.user.UserAuthDto;
import robertli.zero.entity.AccessToken;
import robertli.zero.entity.User;
import robertli.zero.service.UserService;

@Component("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private UserAuthDao userAuthDao;

    @Resource
    private AccessTokenDao accessTokenDao;

    @Override
    public User getUserProfile(String token) {
        AccessToken accessToken = accessTokenDao.get(token);
        if (accessToken == null) {
            return null;
        }
        return accessToken.getUser();
    }

    @Override
    public String putAuth(UserAuthDto userAuthDto) {
        String userType = userAuthDto.getUserType();
        String username = userAuthDto.getUsername().trim();
        userAuthDao.makeAuthId(userType, userAuthDto.getPlatform(), username);
        switch (userType) {
            case UserService.USER_TYPE_ADMIN:
            case UserService.USER_TYPE_GENERAL:
            case UserService.USER_TYPE_STAFF:
                userAuthDao.get(userType);
            default:
                String state = "USER_TYPE_NOT_SUPPORT";
                String message = "this user type is not support";
                String detail = "can't support this userType:" + userType;
                throw new RestException(state, message, detail, HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public void deleteAuth(String token) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

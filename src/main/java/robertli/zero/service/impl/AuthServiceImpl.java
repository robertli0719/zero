/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import robertli.zero.controller.RestException;
import robertli.zero.core.SecurityService;
import robertli.zero.dao.AccessTokenDao;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dto.user.UserAuthDto;
import robertli.zero.dto.user.UserProfileDto;
import robertli.zero.entity.AccessToken;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;
import robertli.zero.entity.UserPlatform;
import robertli.zero.entity.UserType;
import robertli.zero.service.AuthService;
import robertli.zero.service.UserService;

@Component("authService")
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserAuthDao userAuthDao;

    @Resource
    private AccessTokenDao accessTokenDao;

    @Resource
    private SecurityService securityService;

    @Override
    public UserProfileDto getUserProfile(String token) {
        if (token == null) {
            return new UserProfileDto();
        }
        AccessToken accessToken = accessTokenDao.get(token);
        if (accessToken == null) {
            return new UserProfileDto();
        } else if (accessToken.getExpiryDate().before(new Date())) {
            return new UserProfileDto();
        }
        User user = accessToken.getUser();
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setName(user.getName());
        userProfileDto.setAuthLabel(user.getName());
        userProfileDto.setUserPlatformName(user.getUserPlatform().getName());
        userProfileDto.setUserTypeName(user.getUserPlatform().getUserType().getName());
        return userProfileDto;
    }

    private boolean isValidPassword(UserAuthDto userAuthDto, UserAuth userAuth) {
        String orginealPassword = userAuthDto.getPassword();
        User user = userAuth.getUser();
        String salt = user.getPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);
        return password.equals(user.getPassword());
    }

    private void recordAccessToken(String token, User user) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        Date expiryDate = cal.getTime();

        AccessToken accessToken = new AccessToken();
        accessToken.setCreatedDate(new Date());
        accessToken.setExpiryDate(expiryDate);
        accessToken.setToken(token);
        accessToken.setUser(user);
        accessTokenDao.save(accessToken);
    }

    @Override
    public void putAuth(String token, UserAuthDto userAuthDto) {
        String userTypeName = userAuthDto.getUserTypeName();
        String username = userAuthDto.getUsername().trim();
        String userPlatformName = userAuthDto.getUserPlatformName();
        String authId = userAuthDao.makeAuthId(userTypeName, userPlatformName, username);
        UserAuth userAuth = userAuthDao.get(authId);
        switch (userTypeName) {
            case UserService.USER_TYPE_ADMIN:
            case UserService.USER_TYPE_GENERAL:
            case UserService.USER_TYPE_STAFF:
                if (userAuth == null) {
                    throw new RestException("WRONG_AUTH_ID", "username or password wrong", "for this authId, the userAuth is null", HttpStatus.FORBIDDEN);
                }

                UserPlatform userPlatform = userAuth.getUser().getUserPlatform();
                if (userPlatform.getName().equals(userPlatformName) == false) {
                    throw new RestException("WRONG_AUTH_USER_PLATFORM", "userPlatform wrong", "for this authId, userPlatform is not equals", HttpStatus.FORBIDDEN);
                }

                UserType userType = userPlatform.getUserType();
                if (userType.getName().equals(userTypeName) == false) {
                    throw new RestException("WRONG_AUTH_USER_TYPE", "userType wrong", "for this authId, userType is not equals", HttpStatus.FORBIDDEN);
                }
                if (isValidPassword(userAuthDto, userAuth) == false) {
                    throw new RestException("WRONG_AUTH_ID", "username or password wrong", "for this authId, the userAuth is null", HttpStatus.FORBIDDEN);
                }
                recordAccessToken(token, userAuth.getUser());
                return;
            default:
                String state = "USER_TYPE_NOT_SUPPORT";
                String message = "this user type is not support";
                String detail = "can't support this userType:" + userTypeName;
                throw new RestException(state, message, detail);
        }
    }

    @Override
    public void deleteAuth(String token) {
        accessTokenDao.deleteById(token);
    }

}
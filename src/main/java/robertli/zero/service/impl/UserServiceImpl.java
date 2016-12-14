/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.Calendar;
import java.util.Date;
import javax.annotation.Resource;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import robertli.zero.controller.RestException;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.core.SecurityService;
import robertli.zero.dao.AccessTokenDao;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dao.UserDao;
import robertli.zero.dto.user.UserAuthDto;
import robertli.zero.dto.user.UserProfileDto;
import robertli.zero.entity.AccessToken;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;
import robertli.zero.service.UserService;

@Component("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Resource
    private UserAuthDao userAuthDao;

    @Resource
    private AccessTokenDao accessTokenDao;

    @Resource
    private SecurityService securityService;

    @Resource
    private RandomCodeCreater randomCodeCreater;

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
        ModelMapper modelMapper = new ModelMapper();
        UserProfileDto userProfileDto = modelMapper.map(user, UserProfileDto.class);
        return userProfileDto;
    }

    private boolean isValidPassword(UserAuthDto userAuthDto, UserAuth userAuth) {
        String orginealPassword = userAuthDto.getPassword();
        User user = userAuth.getUser();
        String salt = user.getPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);
        return password.equals(user.getPassword());
    }

    private String recordAccessToken(User user) {
        String token = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        Date expiryDate = cal.getTime();

        AccessToken accessToken = new AccessToken();
        accessToken.setCreatedDate(new Date());
        accessToken.setExpiryDate(expiryDate);
        accessToken.setToken(token);
        accessToken.setUser(user);
        accessTokenDao.save(accessToken);
        return token;
    }

    @Override
    public String putAuth(UserAuthDto userAuthDto) {
        String userType = userAuthDto.getUserType();
        String username = userAuthDto.getUsername().trim();
        String authId = userAuthDao.makeAuthId(userType, userAuthDto.getPlatform(), username);
        UserAuth userAuth = userAuthDao.get(authId);
        switch (userType) {
            case UserService.USER_TYPE_ADMIN:
            case UserService.USER_TYPE_GENERAL:
            case UserService.USER_TYPE_STAFF:
                if (userAuth == null) {
                    throw new RestException("WRONG_AUTH_ID", "username or password wrong", "for this authId, the userAuth is null", HttpStatus.FORBIDDEN);
                }
                if (userAuth.getUser().getUserType().getName().equals(userType) == false) {
                    throw new RestException("WRONG_AUTH_USER_TYPE", "userType wrong", "for this authId, userType is not equals", HttpStatus.FORBIDDEN);
                }
                if (isValidPassword(userAuthDto, userAuth) == false) {
                    throw new RestException("WRONG_AUTH_ID", "username or password wrong", "for this authId, the userAuth is null", HttpStatus.FORBIDDEN);
                }
                return recordAccessToken(userAuth.getUser());
            default:
                String state = "USER_TYPE_NOT_SUPPORT";
                String message = "this user type is not support";
                String detail = "can't support this userType:" + userType;
                throw new RestException(state, message, detail);
        }
    }

    @Override
    public void deleteAuth(String token) {
        accessTokenDao.deleteById(token);
    }

}

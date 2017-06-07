/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import robertli.zero.controller.RestException;
import robertli.zero.core.SecurityService;
import robertli.zero.dao.AccessTokenDao;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dto.user.UserAuthDto;
import robertli.zero.dto.user.UserAuthPasswordDto;
import robertli.zero.dto.user.UserProfileDto;
import robertli.zero.entity.AccessToken;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;
import robertli.zero.entity.UserPlatform;
import robertli.zero.entity.UserRoleItem;
import robertli.zero.entity.UserType;
import robertli.zero.service.AuthService;
import robertli.zero.service.UserService;
import robertli.zero.tool.ValidationTool;

@Component("authService")
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserAuthDao userAuthDao;

    @Resource
    private AccessTokenDao accessTokenDao;

    @Resource
    private SecurityService securityService;

    private String pickAuthLabel(User user) {
        final List<UserAuth> authList = user.getUserAuthList();
        if (authList == null || authList.isEmpty()) {
            return user.getName();
        }
        for (final UserAuth auth : authList) {
            if (auth.getUsernameType().equals(UserService.USERNAME_TYPE_EMAIL)) {
                return auth.getLabel();
            }
        }
        for (final UserAuth auth : authList) {
            if (auth.getUsernameType().equals(UserService.USERNAME_TYPE_TELEPHONE)) {
                return auth.getLabel();
            }
        }
        return authList.get(0).getLabel();
    }

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
        final User user = accessToken.getUser();
        final String authLabel = pickAuthLabel(user);
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUid(user.getUid());
        userProfileDto.setName(user.getName());
        userProfileDto.setAuthLabel(authLabel);
        userProfileDto.setUserPlatformName(user.getUserPlatform().getName());
        userProfileDto.setUserTypeName(user.getUserPlatform().getUserType().getName());
        ArrayList<String> roleList = new ArrayList<>();
        for (UserRoleItem role : user.getUserRoleItemList()) {
            String name = role.getUserRole().getName();
            roleList.add(name);
        }
        userProfileDto.setRoleList(roleList);
        return userProfileDto;
    }

    private boolean isValidPassword(User user, String originalPassword) {
        String salt = user.getPasswordSalt();
        String password = securityService.uglifyPassoword(originalPassword, salt);
        return password.equals(user.getPassword());
    }

    private boolean isValidPassword(UserAuthDto userAuthDto, UserAuth userAuth) {
        String originalPassword = userAuthDto.getPassword();
        User user = userAuth.getUser();
        return isValidPassword(user, originalPassword);
    }

    private void recordAccessToken(String token, User user) {
        accessTokenDao.clear();
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

    private String preprocessUsername(String username) {
        if (ValidationTool.checkEmail(username)) {
            return ValidationTool.preprocessEmail(username);
        }
        return username;
    }

    @Override
    public void putAuth(String token, UserAuthDto userAuthDto) {
        final String userTypeName = userAuthDto.getUserTypeName();
        final String usernameInput = userAuthDto.getUsername().trim();
        final String username = preprocessUsername(usernameInput);
        final String userPlatformName = userAuthDto.getUserPlatformName();
        final String authId = userAuthDao.makeAuthId(userTypeName, userPlatformName, username);
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

    @Override
    public void resetPassword(String token, UserAuthPasswordDto userAuthPasswordDto) {
        if (token == null) {
            throw new RestException("UNAUTHORIZED", "For reset your password, you should login first", "AuthService resetPassword can't get token", HttpStatus.UNAUTHORIZED);
        }
        AccessToken accessToken = accessTokenDao.get(token);
        if (accessToken == null) {
            throw new RestException("UNAUTHORIZED", "For reset your password, you should login first", "AuthService.resetPassword gets an invalid token", HttpStatus.UNAUTHORIZED);
        } else if (accessToken.getExpiryDate().before(new Date())) {
            throw new RestException("UNAUTHORIZED", "For reset your password, you should login first", "AuthService.resetPassword gets a timeout token", HttpStatus.UNAUTHORIZED);
        }
        User user = accessToken.getUser();
        if (isValidPassword(user, userAuthPasswordDto.getOldPassword()) == false) {
            throw new RestException("WRONG_AUTH_ID", "current password wrong", "current password wrong", HttpStatus.FORBIDDEN);
        }
        final String passwordSalt = securityService.createPasswordSalt();
        final String password = securityService.uglifyPassoword(userAuthPasswordDto.getNewPassword(), passwordSalt);
        user.setPassword(password);
        user.setPasswordSalt(passwordSalt);
    }

}

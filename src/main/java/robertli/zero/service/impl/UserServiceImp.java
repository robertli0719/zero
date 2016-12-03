/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import robertli.zero.core.GoogleAuthService;
import robertli.zero.core.SecurityService;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dao.UserDao;
import robertli.zero.dao.UserOnlineDao;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;
import robertli.zero.entity.UserOnline;
import robertli.zero.service.UserService;
import robertli.zero.tool.ValidationTool;

/**
 *
 * @author Robert Li
 */
@Component("userService")
public class UserServiceImp implements UserService {

    public static final int SESSION_LIFE_MINUTE = 60;

    @Resource
    private UserDao userDao;

    @Resource
    private UserOnlineDao userOnlineDao;

    @Resource
    private UserAuthDao userAuthDao;

    @Resource
    private SecurityService securityService;

    @Resource
    private GoogleAuthService googleAuthService;

    @Override
    public User getCurrentUser(String sessionId) {
        userOnlineDao.clear(SESSION_LIFE_MINUTE);
        UserOnline userOnline = userOnlineDao.get(sessionId);
        if (userOnline != null) {
            userOnline.setLastActiveDate(new Date());
            return userOnline.getUser();
        }
        return null;
    }

    private User checkUser(String userAuthId, String orginealPassword) {
        UserAuth userAuth = userAuthDao.get(userAuthId);
        if (userAuth == null) {
            return null;
        }
        User user = userAuth.getUser();
        String salt = user.getPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);
        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    private String makeUserAuthId(String userAuth) {
        userAuth = (userAuth != null) ? userAuth.trim() : "";
        if (ValidationTool.checkEmail(userAuth)) {
            return ValidationTool.preprocessEmail(userAuth);
        }
        return userAuth;
    }

    @Override
    public UserLoginResult login(String sessionId, String userAuth, String orginealPassword) {
        System.out.println("sessionId:"+sessionId);
        System.out.println("userAuth:"+userAuth);
        System.out.println("orginealPassword:"+orginealPassword);
        try {
            String userAuthId = makeUserAuthId(userAuth);
            User user = checkUser(userAuthId, orginealPassword + "");
            if (user == null) {
                return UserLoginResult.PASSWORD_WRONG;
            }
            userOnlineDao.clear(SESSION_LIFE_MINUTE);
            if (userOnlineDao.isExist(sessionId)) {
                return UserLoginResult.USER_LOGINED;
            }
            userOnlineDao.saveUserOnline(sessionId, user);
            return UserLoginResult.SUCCESS;
        } catch (RuntimeException re) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return UserLoginResult.DATABASE_EXCEPTION;
        }
    }

    private String getVerifiedEmailFromGoogleResult(Map<String, String> infoMap) {
        if (infoMap.containsKey("email") == false) {
            return null;
        } else if (infoMap.containsKey("email_verified") == false) {
            return null;
        } else if (infoMap.get("email_verified").equals("true") == false) {
            return null;
        }
        return infoMap.get("email");
    }

    private User recordNewUserForGoogleAccount(String userAuthId, String email, String name) {
        final String orginealPassword = securityService.createPasswordSalt();
        final String passwordSalt = securityService.createPasswordSalt();
        final String password = securityService.uglifyPassoword(orginealPassword, passwordSalt);

        User user = userDao.saveUser(name, password, passwordSalt);
        userAuthDao.saveUserAuth(userAuthId, email, "email", user);
        return user;
    }

    @Override
    public UserLoginByGoogleResult loginByGoogle(String sessionId, String token) {
        userOnlineDao.clear(SESSION_LIFE_MINUTE);
        if (userOnlineDao.isExist(sessionId)) {
            return UserLoginByGoogleResult.USER_LOGINED;
        }
        Map<String, String> infoMap;
        try {
            infoMap = googleAuthService.getInfo(token);
        } catch (IOException ex) {
            return UserLoginByGoogleResult.FAIL;
        }
        String email = getVerifiedEmailFromGoogleResult(infoMap);
        if (email == null) {
            return UserLoginByGoogleResult.NO_VERIFIED_EMAIL;
        }
        String userAuthId = makeUserAuthId(email);
        UserAuth userAuth = userAuthDao.get(userAuthId);
        User user = userAuth != null ? userAuth.getUser() : null;
        if (user == null) {
            String name = infoMap.get("name") + "";
            user = recordNewUserForGoogleAccount(userAuthId, email, name);
        }
        userOnlineDao.saveUserOnline(sessionId, user);
        return UserLoginByGoogleResult.SUCCESS;
    }

    @Override
    public boolean logout(String sessionId) {
        try {
            UserOnline userOnline = userOnlineDao.get(sessionId);
            if (userOnline != null) {
                userOnlineDao.delete(userOnline);
            }
        } catch (RuntimeException re) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return true;
        }
        return false;
    }

}

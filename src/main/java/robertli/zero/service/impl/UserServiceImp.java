/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.service.impl;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import robertli.zero.core.SecurityService;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dao.UserOnlineDao;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;
import robertli.zero.entity.UserOnline;
import robertli.zero.service.UserService;

/**
 *
 * @author Robert Li
 */
@Component("userService")
public class UserServiceImp implements UserService {

    public static final int SESSION_LIFE_MINUTE = 60;

    @Resource
    private UserOnlineDao userOnlineDao;

    @Resource
    private UserAuthDao userAuthDao;

    @Resource
    private SecurityService securityService;

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

    @Override
    public UserLoginResult login(String sessionId, String userAuthId, String orginealPassword) {
        try {
            User user = checkUser(userAuthId, orginealPassword);
            if (user == null) {
                return UserLoginResult.PASSWORD_WRONG;
            }
            userOnlineDao.clear(SESSION_LIFE_MINUTE);
            UserOnline userOnline = userOnlineDao.get(sessionId);
            if (userOnline != null) {
                return UserLoginResult.USER_LOGINED;
            }
            userOnline = new UserOnline();
            userOnline.setSessionId(sessionId);
            userOnline.setUser(user);
            userOnline.setLastActiveDate(new Date());
            userOnlineDao.save(userOnline);
            return UserLoginResult.SUCCESS;
        } catch (RuntimeException re) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return UserLoginResult.DATABASE_EXCEPTION;
        }
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

/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dao.UserPlatformDao;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;
import robertli.zero.entity.UserPlatform;

@Component("userAuthDao")
public class UserAuthDaoImpl extends GenericHibernateDao<UserAuth, String> implements UserAuthDao {

    @Resource
    private UserPlatformDao userPlatformDao;

    @Override
    public String makeAuthId(String userTypeName, String platformName, String username) {
        return userTypeName + ":" + platformName + ":" + username;
    }

    @Override
    public UserAuth saveUserAuth(String userPlatformName, String username, String label, String usernameType, User user) {
        UserPlatform userPlatform = userPlatformDao.get(userPlatformName);
        String userTypeName = userPlatform.getUserType().getName();
        String authId = makeAuthId(userTypeName, userPlatformName, username);

        UserAuth userAuth = new UserAuth();
        userAuth.setAuthId(authId);
        userAuth.setLabel(label);
        userAuth.setUsernameType(usernameType);
        userAuth.setUser(user);
        save(userAuth);
        return userAuth;
    }

}

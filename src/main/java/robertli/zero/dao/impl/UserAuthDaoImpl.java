/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.entity.User;
import robertli.zero.entity.UserAuth;

@Component("userAuthDao")
public class UserAuthDaoImpl extends GenericHibernateDao<UserAuth, String> implements UserAuthDao {

    @Override
    public UserAuth saveUserAuth(String authId, String label, String type, User user) {
        UserAuth userAuth = new UserAuth();
        userAuth.setAuthId(authId);
        userAuth.setLabel(label);
        userAuth.setType(type);
        userAuth.setUser(user);
        save(userAuth);
        return userAuth;
    }

}

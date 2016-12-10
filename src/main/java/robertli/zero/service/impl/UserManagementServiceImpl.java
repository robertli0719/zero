/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.SecurityService;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dao.UserDao;
import robertli.zero.dao.UserTypeDao;
import robertli.zero.entity.User;
import robertli.zero.entity.UserType;
import robertli.zero.service.UserManagementService;

@Component("userManagementService")
public class UserManagementServiceImpl implements UserManagementService {
    
    @Resource
    private SecurityService securityService;
    
    @Resource
    private UserDao userDao;
    
    @Resource
    private UserAuthDao userAuthDao;
    
    @Resource
    private UserTypeDao userTypeDao;
    
    @Override
    public void addUser(String type, String clientId, String usernameType, String username, String orginealPassword, String nickname) {
        String salt = securityService.createPasswordSalt();
        String password = securityService.uglifyPassoword(orginealPassword, salt);
        
        UserType userType = userTypeDao.get(type);
        User user = new User();
        user.setName(nickname);
        user.setPassword(password);
        user.setPasswordSalt(salt);
        user.setUserType(userType);
        userDao.save(user);
        
        userAuthDao.saveUserAuth(type, clientId, username, username, usernameType, user);
    }
    
    @Override
    public void setLock(int userId, boolean lock) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<User> getUserList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<User> getUserListByType(String type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

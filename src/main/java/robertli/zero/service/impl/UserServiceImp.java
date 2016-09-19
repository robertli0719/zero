/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import robertli.zero.dao.UserDao;
import robertli.zero.entity.User;
import robertli.zero.entity.UserRegister;
import robertli.zero.service.UserService;

/**
 *
 * @author Robert Li
 */
@Component("userService")
public class UserServiceImp implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public User getCurrentUser(String sessionId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserLoginResult login(String sessionId, String auth, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserLogoffResult logoff(String sessionId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserRegisterResult register(UserRegister register, String passwordAgain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void test() {
        System.out.println("access....");

        User user = new User();
        user.setName("AA");
        userDao.save(user);
        if (1 + 1 > 2) {
            throw new RuntimeException();
        }
        user = new User();
        user.setName("BB");
        userDao.save(user);
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        System.out.println("rollback hehehehe......");
    }

    @Override
    public UserLoginResult loginByGoogle(String sessionId, String googleIdToken) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

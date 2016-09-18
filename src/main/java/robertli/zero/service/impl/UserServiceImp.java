/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.service.impl;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import robertli.zero.dao.UserDao;
import robertli.zero.entity.User;
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
    public void addUser(String name) {
        User user = new User();
        user.setName(name);
        userDao.save(user);
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
    }

}

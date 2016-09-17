/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Robert Li
 */
public class UserServiceTest {

    private static void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = (UserService) context.getBean("userService");
        userService.test();
    }

    public static void main(String args[]) {
        test();
    }
}

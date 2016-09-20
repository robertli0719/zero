/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.service;

import java.util.UUID;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.entity.User;

/**
 *
 * @author Robert Li
 */
public class UserServiceTest {

    private UserService userService;
    private UserRegisterService userRegisterService;

    private void testGetCurrentUser() {
        String sessionId = UUID.randomUUID().toString();
        User user = userService.getCurrentUser(sessionId);

        System.out.println(user);
    }

    private void testVerifiyRegister() {
        userRegisterService.verifiyRegister("aQcLi9yYbj2Fjlc57K6IkePHWnY7S0");
    }

    private void test() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        userService = (UserService) context.getBean("userService");
        userRegisterService = (UserRegisterService) context.getBean("userRegisterService");
        //testGetCurrentUser();
        testVerifiyRegister();
//        Thread arr[] = new Thread[100];
//        for (int i = 0; i < arr.length; i++) {
//            arr[i] = new Thread() {
//                @Override
//                public void run() {
//                    try {
//                    } catch (Exception ex) {
//                        //Logger.getLogger(UserServiceTest.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            };
//        }
//        for (Thread th : arr) {
//            th.start();
//        }
//        for (Thread th : arr) {
//            th.join();
//        }
    }

    public static void main(String args[]) throws Exception {

        UserServiceTest userServcieTest = new UserServiceTest();
        userServcieTest.test();
    }
}

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

        
    private static void test() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        UserService userService = (UserService) context.getBean("userService");
        Thread arr[] = new Thread[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new Thread() {
                @Override
                public void run() {
                    try {
                    } catch (Exception ex) {
                        //Logger.getLogger(UserServiceTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
        }
        for (Thread th : arr) {
            th.start();
        }
        for (Thread th : arr) {
            th.join();
        }
    }

    public static void main(String args[]) throws Exception {
        test();
    }
}

/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.service.UserEmailBuilder;
import robertli.zero.tool.MD5;

/**
 *
 * @author Robert Li
 */
public class UserEmailBuilderTest {
    
    public static void testUserEmailBuilder(){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        UserEmailBuilder userEmailBuilder = (UserEmailBuilder) context.getBean("userEmailBuilder");
        System.out.println(userEmailBuilder.buildPasswordResetTokenEmail("email@test.com", "tom", MD5.count("sdf")));
    }
    
     public static void main(String args[]) {
        testUserEmailBuilder();
    }
}

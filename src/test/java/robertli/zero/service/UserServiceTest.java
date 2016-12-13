/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.dto.user.UserProfileDto;

/**
 *
 * @author Robert Li
 */
public class UserServiceTest {

    private final UserService userService;
    private final RandomCodeCreater randomCodeCreater;
    private final Random rand;

    public UserServiceTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        userService = (UserService) context.getBean("userService");
        randomCodeCreater = (RandomCodeCreater) context.getBean("randomCodeCreater");
        rand = new Random();
    }

    //Token不正确的时候，返回一个空的DTO
    public void test1() {
        String token = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        UserProfileDto userProfile = userService.getUserProfile(token);
        assertTrue(userProfile != null);
        assertTrue(userProfile.getAuthLabel() == null);
        assertTrue(userProfile.getName() == null);
        assertTrue(userProfile.getTelephone() == null);
        assertTrue(userProfile.getUserType() == null);
    }

    //Token为null时候，返回一个空的DTO
    public void test2() {
        UserProfileDto userProfile = userService.getUserProfile(null);
        assertTrue(userProfile != null);
        assertTrue(userProfile.getAuthLabel() == null);
        assertTrue(userProfile.getName() == null);
        assertTrue(userProfile.getTelephone() == null);
        assertTrue(userProfile.getUserType() == null);
    }

    public void test() throws UnsupportedEncodingException {
        test1();
        test2();
    }

    public static void main(String args[]) throws UnsupportedEncodingException {
        UserServiceTest userServiceTest = new UserServiceTest();
        userServiceTest.test();
    }

}

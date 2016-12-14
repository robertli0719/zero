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
import robertli.zero.controller.RestException;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.dto.user.UserAuthDto;
import robertli.zero.dto.user.UserProfileDto;

/**
 *
 * @author Robert Li
 */
public class UserServiceTest {

    private final UserService userService;
    private final RandomCodeCreater randomCodeCreater;
    private final UserManagementService userManagementService;
    private final Random rand;

    public UserServiceTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        userService = (UserService) context.getBean("userService");
        userManagementService = (UserManagementService) context.getBean("userManagementService");
        randomCodeCreater = (RandomCodeCreater) context.getBean("randomCodeCreater");
        rand = new Random();
    }

    //getUserProfile Token不正确的时候，返回一个空的DTO
    public void test1() {
        String token = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        UserProfileDto userProfile = userService.getUserProfile(token);
        assertTrue(userProfile != null);
        assertTrue(userProfile.getAuthLabel() == null);
        assertTrue(userProfile.getName() == null);
        assertTrue(userProfile.getTelephone() == null);
        assertTrue(userProfile.getUserType() == null);
    }

    //getUserProfile Token为null时候，返回一个空的DTO
    public void test2() {
        UserProfileDto userProfile = userService.getUserProfile(null);
        assertTrue(userProfile != null);
        assertTrue(userProfile.getAuthLabel() == null);
        assertTrue(userProfile.getName() == null);
        assertTrue(userProfile.getTelephone() == null);
        assertTrue(userProfile.getUserType() == null);
    }

    //常规登陆登出测试
    public void test3() {
        String username = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        String password = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        String nickname = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        userManagementService.addUser(UserService.USER_TYPE_GENERAL, "default", "string", username, password, nickname);

        UserAuthDto userAuthDto = new UserAuthDto();
        userAuthDto.setUsername(username);
        userAuthDto.setPlatform("default");
        userAuthDto.setPassword(password);
        userAuthDto.setUserType(UserService.USER_TYPE_GENERAL);
        String token = userService.putAuth(userAuthDto);
        assertTrue(token != null);
        assertTrue(userService.getUserProfile(token).getName().equals(nickname));
        userService.deleteAuth(token);
        assertTrue(userService.getUserProfile(token).getName() == null);

        //测试错误密码时
        userAuthDto.setPassword("wrong_password");
        boolean flag = false;
        try {
            userService.putAuth(userAuthDto);
        } catch (RestException re) {
            flag = true;
        }
        assertTrue(flag);
    }

    public void test() throws UnsupportedEncodingException {
        test1();
        test2();
        test3();
    }

    public static void main(String args[]) throws UnsupportedEncodingException {
        UserServiceTest userServiceTest = new UserServiceTest();
        userServiceTest.test();
    }

}

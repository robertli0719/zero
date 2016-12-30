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
import robertli.zero.dto.user.UserDto;
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
        assertTrue(userProfile.getUserTypeName() == null);
    }

    //getUserProfile Token为null时候，返回一个空的DTO
    public void test2() {
        UserProfileDto userProfile = userService.getUserProfile(null);
        assertTrue(userProfile != null);
        assertTrue(userProfile.getAuthLabel() == null);
        assertTrue(userProfile.getName() == null);
        assertTrue(userProfile.getTelephone() == null);
        assertTrue(userProfile.getUserTypeName() == null);
    }

    //常规登陆登出测试
    public void test3() {
        final String username = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        final String password = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        final String nickname = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        final String platformName = UserService.USER_PLATFORM_GENERAL;
        final String usernameType = UserService.USERNAME_TYPE_STRING;

        UserDto userDto = new UserDto();
        userDto.setLabel(username);
        userDto.setName(nickname);
        userDto.setPassword(password);
        userDto.setTelephone(null);
        userDto.setUserPlatformName(platformName);
        userDto.setUsername(username);
        userDto.setUsernameType(usernameType);

        userManagementService.addUser(userDto);

        UserAuthDto userAuthDto = new UserAuthDto();
        userAuthDto.setUsername(username);
        userAuthDto.setUserPlatformName(platformName);
        userAuthDto.setPassword(password);
        userAuthDto.setUserTypeName(UserService.USER_TYPE_GENERAL);
        String token = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        userService.putAuth(token, userAuthDto);
        assertTrue(token != null);
        assertTrue(userService.getUserProfile(token).getName().equals(nickname));
        userService.deleteAuth(token);
        assertTrue(userService.getUserProfile(token).getName() == null);

        //测试错误密码时
        userAuthDto.setPassword("wrong_password");
        boolean flag = false;
        try {
            token = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
            userService.putAuth(token, userAuthDto);
        } catch (RestException re) {
            flag = true;
        }
        assertTrue(flag);
    }

    //多平台重用username测试
    public void test4() {
        final String username = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        final String password1 = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        final String password2 = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);

        UserDto userDto1 = new UserDto();
        userDto1.setLabel(username);
        userDto1.setName("testUser1");
        userDto1.setPassword(password1);
        userDto1.setUserPlatformName(UserService.USER_PLATFORM_ADMIN);
        userDto1.setUsername(username);
        userDto1.setUsernameType(UserService.USERNAME_TYPE_STRING);

        UserDto userDto2 = new UserDto();
        userDto2.setLabel(username);
        userDto2.setName("testUser2");
        userDto2.setPassword(password2);
        userDto2.setUserPlatformName(UserService.USER_PLATFORM_GENERAL);
        userDto2.setUsername(username);
        userDto2.setUsernameType(UserService.USERNAME_TYPE_STRING);

        userManagementService.addUser(userDto1);
        userManagementService.addUser(userDto2);

        UserAuthDto userAuthDto1 = new UserAuthDto();
        userAuthDto1.setUsername(username);
        userAuthDto1.setUserPlatformName(UserService.USER_PLATFORM_ADMIN);
        userAuthDto1.setPassword(password1);
        userAuthDto1.setUserTypeName(UserService.USER_TYPE_ADMIN);
        String token1 = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        userService.putAuth(token1, userAuthDto1);
        assertTrue(userService.getUserProfile(token1).getName().equals("testUser1"));

        UserAuthDto userAuthDto2 = new UserAuthDto();
        userAuthDto2.setUsername(username);
        userAuthDto2.setUserPlatformName(UserService.USER_PLATFORM_GENERAL);
        userAuthDto2.setPassword(password2);
        userAuthDto2.setUserTypeName(UserService.USER_TYPE_GENERAL);
        String token2 = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        userService.putAuth(token2, userAuthDto2);
        assertTrue(userService.getUserProfile(token2).getName().equals("testUser2"));
    }

    public void test() throws UnsupportedEncodingException {
        test1();
        test2();
        test3();
        test4();
    }

    public static void main(String args[]) throws UnsupportedEncodingException {
        UserServiceTest userServiceTest = new UserServiceTest();
        userServiceTest.test();
    }

}

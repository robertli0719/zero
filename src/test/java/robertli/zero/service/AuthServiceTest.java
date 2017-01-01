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
import robertli.zero.dto.user.StaffUserDto;
import robertli.zero.dto.user.UserAuthDto;
import robertli.zero.dto.user.UserProfileDto;

/**
 *
 * @author Robert Li
 */
public class AuthServiceTest {

    private final AuthService authService;
    private final RandomCodeCreater randomCodeCreater;
    private final UserService userService;
    private final StaffUserService staffUserService;
    private final Random rand;

    public AuthServiceTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        authService = (AuthService) context.getBean("authService");
        userService = (UserService) context.getBean("userService");
        staffUserService = (StaffUserService) context.getBean("staffUserService");
        randomCodeCreater = (RandomCodeCreater) context.getBean("randomCodeCreater");
        rand = new Random();
    }

    //getUserProfile Token不正确的时候，返回一个空的DTO
    public void test1() {
        String token = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        UserProfileDto userProfile = authService.getUserProfile(token);
        assertTrue(userProfile != null);
        assertTrue(userProfile.getAuthLabel() == null);
        assertTrue(userProfile.getName() == null);
        assertTrue(userProfile.getTelephone() == null);
        assertTrue(userProfile.getUserTypeName() == null);
    }

    //getUserProfile Token为null时候，返回一个空的DTO
    public void test2() {
        UserProfileDto userProfile = authService.getUserProfile(null);
        assertTrue(userProfile != null);
        assertTrue(userProfile.getAuthLabel() == null);
        assertTrue(userProfile.getName() == null);
        assertTrue(userProfile.getTelephone() == null);
        assertTrue(userProfile.getUserTypeName() == null);
    }

    //常规登陆登出测试
    public void test3() {
        final String platformName = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        userService.addUserPlatform(UserService.USER_TYPE_STAFF, platformName);

        final String username = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        final String password = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);

        StaffUserDto staffUserDto = new StaffUserDto();
        staffUserDto.setLocked(false);
        staffUserDto.setPassword(password);
        staffUserDto.setUserPlatformName(platformName);
        staffUserDto.setUsername(username);
        staffUserService.addStaffUser(staffUserDto);

        UserAuthDto userAuthDto = new UserAuthDto();
        userAuthDto.setUsername(username);
        userAuthDto.setUserPlatformName(platformName);
        userAuthDto.setPassword(password);
        userAuthDto.setUserTypeName(UserService.USER_TYPE_STAFF);
        String token = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        authService.putAuth(token, userAuthDto);
        assertTrue(token != null);
        assertTrue(authService.getUserProfile(token).getName().equals(username));
        authService.deleteAuth(token);
        assertTrue(authService.getUserProfile(token).getName() == null);

        //测试错误密码时
        userAuthDto.setPassword("wrong_password");
        boolean flag = false;
        try {
            token = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
            authService.putAuth(token, userAuthDto);
        } catch (RestException re) {
            flag = true;
        }
        assertTrue(flag);
    }

    //多平台重用username测试
    public void test4() {
        final String platformName1 = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        final String platformName2 = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        userService.addUserPlatform(UserService.USER_TYPE_STAFF, platformName1);
        userService.addUserPlatform(UserService.USER_TYPE_STAFF, platformName2);

        final String username = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        final String password1 = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        final String password2 = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);

        StaffUserDto staffUserDto1 = new StaffUserDto();
        staffUserDto1.setLocked(false);
        staffUserDto1.setPassword(password1);
        staffUserDto1.setUserPlatformName(platformName1);
        staffUserDto1.setUsername(username);
        staffUserService.addStaffUser(staffUserDto1);

        StaffUserDto staffUserDto2 = new StaffUserDto();
        staffUserDto2.setLocked(false);
        staffUserDto2.setPassword(password2);
        staffUserDto2.setUserPlatformName(platformName2);
        staffUserDto2.setUsername(username);
        staffUserService.addStaffUser(staffUserDto2);

        UserAuthDto userAuthDto1 = new UserAuthDto();
        userAuthDto1.setUsername(username);
        userAuthDto1.setUserPlatformName(platformName1);
        userAuthDto1.setPassword(password1);
        userAuthDto1.setUserTypeName(UserService.USER_TYPE_STAFF);
        String token1 = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        authService.putAuth(token1, userAuthDto1);
        assertTrue(authService.getUserProfile(token1).getName().equals(username));

        UserAuthDto userAuthDto2 = new UserAuthDto();
        userAuthDto2.setUsername(username);
        userAuthDto2.setUserPlatformName(platformName2);
        userAuthDto2.setPassword(password2);
        userAuthDto2.setUserTypeName(UserService.USER_TYPE_STAFF);
        String token2 = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        authService.putAuth(token2, userAuthDto2);
        assertTrue(authService.getUserProfile(token2).getName().equals(username));
    }

    public void test() throws UnsupportedEncodingException {
        test1();
        test2();
        test3();
        test4();
    }

    public static void main(String args[]) throws UnsupportedEncodingException {
        AuthServiceTest authServiceTest = new AuthServiceTest();
        authServiceTest.test();
    }

}

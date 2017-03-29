/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.dto.user.UserPlatformDto;
import robertli.zero.dto.user.UserRoleDto;
import robertli.zero.dto.user.UserTypeDto;

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

    private boolean existUserType(String typeName) {
        List<UserTypeDto> userTypeList = userService.getUserTypeList(0, 10000).getResultList();
        for (UserTypeDto t : userTypeList) {
            if (t.getName().equals(typeName)) {
                return true;
            }
        }
        return false;
    }

    private boolean existUserPlatform(String platformName) {
        List<UserPlatformDto> userPlatformList = userService.getUserPlatformList(0, 10000).getResultList();
        for (UserPlatformDto t : userPlatformList) {
            if (t.getName().equals(platformName)) {
                return true;
            }
        }
        return false;
    }

    private boolean existUserRole(String roleName) {
        List<UserRoleDto> userPlatformList = userService.getUserRoleList(0, 10000).getResultList();
        for (UserRoleDto t : userPlatformList) {
            if (t.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    private long countUserTypeList() {
        return userService.getUserTypeList(0, 1).getCount();
    }

    //test UserType
    public void test1() {
        String userTypeName = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);

        long beforeListSize = countUserTypeList();
        assertFalse(existUserType(userTypeName));

        userService.addUserType(userTypeName);
        long afterListSize = countUserTypeList();
        assertTrue(existUserType(userTypeName));
        assertTrue(afterListSize - beforeListSize == 1);

        userService.deleteUserType(userTypeName);
        long finalListSize = countUserTypeList();
        assertFalse(existUserType(userTypeName));
        assertTrue(beforeListSize == finalListSize);
    }

    private long countUserPlatform() {
        return userService.getUserPlatformList(0, 1).getCount();
    }

    //test UserPlatform
    public void test2() {
        String userTypeName = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        String userPlatformName = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        userService.addUserType(userTypeName);

        long beforeListSize = countUserPlatform();
        assertFalse(existUserPlatform(userPlatformName));

        userService.addUserPlatform(userTypeName, userPlatformName);
        long afterListSize = countUserPlatform();
        assertTrue(existUserPlatform(userPlatformName));
        assertTrue(afterListSize - beforeListSize == 1);

        userService.deleteUserPlatform(userPlatformName);
        long finalListSize = countUserPlatform();
        assertFalse(existUserPlatform(userPlatformName));
        assertTrue(beforeListSize == finalListSize);
    }

    private long countUserRole() {
        return userService.getUserRoleList(0, 1).getCount();
    }

    //test UserRole
    public void test3() {
        String userRoleName = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);

        long beforeListSize = countUserRole();
        assertFalse(existUserRole(userRoleName));

        userService.addUserRole(userRoleName);
        long afterListSize = countUserRole();
        assertTrue(existUserRole(userRoleName));
        assertTrue(afterListSize - beforeListSize == 1);

        userService.deleteUserRole(userRoleName);
        long finalListSize = countUserRole();
        assertFalse(existUserRole(userRoleName));
        assertTrue(beforeListSize == finalListSize);
    }

    public void test() {
        test1();
        test2();
        test3();
    }

    public static void main(String args[]) {
        UserServiceTest test = new UserServiceTest();
        test.test();
    }
}

/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.List;
import java.util.Random;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.core.ClientAccessTokenManager;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.dto.user.AdminUserDto;
import robertli.zero.dto.user.UserAuthDto;
import robertli.zero.dto.user.UserProfileDto;

/**
 *
 * @author Robert Li
 */
public class AdminUserServiceTest {

    private final AdminUserService adminUserService;
    private final ClientAccessTokenManager clientAccessTokenManager;
    private final AuthService authService;
    private final RandomCodeCreater randomCodeCreater;
    private final Random rand;

    public AdminUserServiceTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        adminUserService = (AdminUserService) context.getBean("adminUserService");
        clientAccessTokenManager = (ClientAccessTokenManager) context.getBean("clientAccessTokenManager");
        authService = (AuthService) context.getBean("authService");
        randomCodeCreater = (RandomCodeCreater) context.getBean("randomCodeCreater");
        rand = new Random();
    }

    private void addAdmin(String username, String password) {
        AdminUserDto adminUserDto = new AdminUserDto();
        adminUserDto.setLocked(false);
        adminUserDto.setUsername(username);
        adminUserDto.setPassword(password);
        adminUserService.addAdminUser(adminUserDto);
    }

    private UserProfileDto adminLogin(String username, String password) {
        String accessTokenO = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        String accessToken = clientAccessTokenManager.countAccessToken(accessTokenO);

        UserAuthDto userAuthDto = new UserAuthDto();
        userAuthDto.setUserTypeName(UserService.USER_TYPE_ADMIN);
        userAuthDto.setUserPlatformName(UserService.USER_PLATFORM_ADMIN);
        userAuthDto.setUsername(username);
        userAuthDto.setPassword(password);
        authService.putAuth(accessToken, userAuthDto);
        return authService.getUserProfile(accessToken);
    }

    //增删一个Admin
    public void test1() {
        final String username = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        final String password = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        final long adminListSize = adminUserService.getAdminUserList(0, 100).getCount();

        assertFalse(adminUserService.isAdminUser(username));
        assertFalse(adminUserService.isRoot(username));

        addAdmin(username, password);
        long listSizeAfterAdd = adminUserService.getAdminUserList(0, 100).getCount();
        assertTrue(listSizeAfterAdd == adminListSize + 1);
        assertTrue(adminUserService.isAdminUser(username));
        assertFalse(adminUserService.isRoot(username));

        adminUserService.addRootRole(username);
        assertTrue(adminUserService.isAdminUser(username));
        assertTrue(adminUserService.isRoot(username));

        adminUserService.deleteRootRole(username);
        assertTrue(adminUserService.isAdminUser(username));
        assertFalse(adminUserService.isRoot(username));

        adminUserService.deleteAdminUser(username);
        long listSizeAfterDelete = adminUserService.getAdminUserList(0, 100).getCount();
        assertTrue(listSizeAfterDelete == adminListSize);
        assertFalse(adminUserService.isAdminUser(username));
        assertFalse(adminUserService.isRoot(username));
    }

    //test login
    public void test2() {
        final String username = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        final String password = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        final long adminListSize = adminUserService.getAdminUserList(0, 100).getCount();

        addAdmin(username, password);
        long listSizeAfterAdd = adminUserService.getAdminUserList(0, 100).getCount();
        assertTrue(listSizeAfterAdd == adminListSize + 1);

        UserProfileDto userProfile = adminLogin(username, password);
        assertTrue(userProfile != null);
        assertTrue(userProfile.getAuthLabel().equals(username));
        assertTrue(userProfile.getName().equals(username));
        assertTrue(userProfile.getTelephone() == null);
        assertTrue(userProfile.getUserPlatformName().equals(UserService.USER_PLATFORM_ADMIN));
        assertTrue(userProfile.getUserTypeName().equals(UserService.USER_TYPE_ADMIN));
    }

    public void test() {
        test1();
        test2();
    }

    public static void main(String args[]) {
        AdminUserServiceTest test = new AdminUserServiceTest();
        test.test();
    }
}

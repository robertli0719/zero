/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.core.AppConfiguration;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.entity.Admin;
import robertli.zero.service.AdminService.AdminLoginResult;
import robertli.zero.test.StressTest;

/**
 * This unit test need the root password equals to the initAdminPassword in
 * appConfigration.
 *
 * @author Robert Li
 */
public class AdminManagementServiceTest {

    private final AdminService adminService;
    private final AdminManagementService adminManagementService;
    private final RandomCodeCreater randomCodeCreater;
    private final AppConfiguration appConfiguration;

    public AdminManagementServiceTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        appConfiguration = (AppConfiguration) context.getBean("appConfiguration");
        adminManagementService = (AdminManagementService) context.getBean("adminManagementService");
        adminService = (AdminService) context.getBean("adminService");
        randomCodeCreater = (RandomCodeCreater) context.getBean("randomCodeCreater");
        adminService.init();
    }

    private String createSessionId() {
        return randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.HEXADECIMAL);
    }

    //正常添加，登陆，登出，重设密码，挂起，激活，删除
    private void testRegister1() {
        String rootPwd = appConfiguration.getInitAdminPassword();
        String sessionId = createSessionId();
        AdminLoginResult result = adminService.login(sessionId, "root", rootPwd);
        assertTrue(result == result.SUCCESS);//如果改过密码则测试会出错

        //添加
        String username = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);
        String password = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);
        boolean fail = adminManagementService.addAdmin(sessionId, username, password);
        assertFalse(fail);

        String adminSessionId = createSessionId();
        Admin admin = adminService.getCurrentAdmin(adminSessionId);
        assertTrue(admin == null);

        //登陆
        adminService.login(adminSessionId, username, password);
        admin = adminService.getCurrentAdmin(adminSessionId);
        assertTrue(admin != null);
        assertTrue(admin.getUsername().equals(username));

        //登出
        adminService.logout(adminSessionId);
        admin = adminService.getCurrentAdmin(adminSessionId);
        assertTrue(admin == null);

        //重设密码
        String newPassword = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);
        fail = adminManagementService.resetPassword(sessionId, username, newPassword);
        assertFalse(fail);

        adminService.login(adminSessionId, username, password);
        admin = adminService.getCurrentAdmin(adminSessionId);
        assertTrue(admin == null);
        adminService.login(adminSessionId, username, newPassword);
        admin = adminService.getCurrentAdmin(adminSessionId);
        assertTrue(admin != null);
        assertTrue(admin.getUsername().equals(username));

        //挂起
        fail = adminManagementService.setSuspendStatus(sessionId, username, true);
        assertFalse(fail);
        admin = adminService.getCurrentAdmin(adminSessionId);//被踢出
        assertTrue(admin == null);
        adminService.login(adminSessionId, username, newPassword);
        admin = adminService.getCurrentAdmin(adminSessionId);
        assertTrue(admin == null);

        //激活
        fail = adminManagementService.setSuspendStatus(sessionId, username, false);
        assertFalse(fail);
        adminService.login(adminSessionId, username, newPassword);
        admin = adminService.getCurrentAdmin(adminSessionId);
        assertTrue(admin != null);
        assertTrue(admin.getUsername().equals(username));
    }

    public void test() {
        testRegister1();
    }

    public void stressTest() throws InterruptedException {
        StressTest stressTest = new StressTest();
        stressTest.setNumberOfGroup(10);
        stressTest.setThreadNumberPerGroup(8);

        stressTest.test("myTest", () -> {
            test();
        });
    }

    public static void main(String args[]) throws Exception {
        AdminManagementServiceTest adminManagementServiceTest = new AdminManagementServiceTest();
        //adminManagementServiceTest.stressTest();
        adminManagementServiceTest.test();
    }
}

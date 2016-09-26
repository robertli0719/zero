/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.core.impl.EmailSenderImpl;
import robertli.zero.dao.UserRegisterDao;
import robertli.zero.entity.User;
import robertli.zero.entity.UserRegister;
import robertli.zero.service.UserService.UserLoginResult;
import robertli.zero.test.StressTest;

/**
 *
 * @author Robert Li
 */
public class UserServiceTest {

    private final UserService userService;
    private final UserRegisterService userRegisterService;
    private final UserRegisterDao userRegisterDao;
    private final RandomCodeCreater randomCodeCreater;
    private final Random rand = new Random();

    public UserServiceTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        userService = (UserService) context.getBean("userService");
        userRegisterService = (UserRegisterService) context.getBean("userRegisterService");
        userRegisterDao = (UserRegisterDao) context.getBean("userRegisterDao");
        randomCodeCreater = (RandomCodeCreater) context.getBean("randomCodeCreater");

        EmailSenderImpl emailSender = (EmailSenderImpl) context.getBean("emailSender");
        emailSender.setTest(true);
    }

    private String createSessionId() {
        return randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.HEXADECIMAL);
    }

    private void addUser(String email, String name, String password) {
        userRegisterService.registerByEmail(email, password, password, name);
        UserRegister userRegister = userRegisterDao.get(email);
        String verifiedCode = userRegister.getVerifiedCode();
        userRegisterService.verifiyRegister(verifiedCode);
    }

    //正常注册后登陆并登出
    private void test1() throws InterruptedException {
        String email = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX) + "@gmail.com";
        String name = randomCodeCreater.createRandomCode(5, RandomCodeCreater.CodeType.MIX);
        String password = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);
        addUser(email, name, password);
        String sessionId = createSessionId();

        User user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);
        UserLoginResult result = userService.login(sessionId, email, password);
        assertTrue(result == UserLoginResult.SUCCESS);

        for (int i = 0; i < 100; i++) {
            user = userService.getCurrentUser(sessionId);
            assertTrue(user != null);
            assertTrue(user.getName().equals(name));
            Thread.sleep(rand.nextInt(10));
        }
        userService.logout(sessionId);

        user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);
    }

    //正常情况下邮件大小写或带点，带前后空格的情况
    private void test2() {
        String part = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);
        String email = "ydfys" + part + "@gmail.com";
        String name = randomCodeCreater.createRandomCode(5, RandomCodeCreater.CodeType.MIX);
        String password = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);
        addUser(email, name, password);
        String sessionId = createSessionId();

        User user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);
        UserLoginResult result = userService.login(sessionId, email, password);
        assertTrue(result == UserLoginResult.SUCCESS);
        userService.logout(sessionId);

        user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);
        email = "  Yd.fy.s" + part + "@gmail.com  ";

        result = userService.login(sessionId, email, password);
        assertTrue(result == UserLoginResult.SUCCESS);
        user = userService.getCurrentUser(sessionId);
        assertTrue(user != null);
    }

    //用户名或密码错误的情况
    private void test3() {
        String email = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX) + "@gmail.com";
        String name = randomCodeCreater.createRandomCode(5, RandomCodeCreater.CodeType.MIX);
        String password = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);
        addUser(email, name, password);
        String sessionId = createSessionId();

        User user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);
        UserLoginResult result = userService.login(sessionId, email + "d", password);
        assertTrue(result == UserLoginResult.PASSWORD_WRONG);
        user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);

        result = userService.login(sessionId, email, password + "d");
        assertTrue(result == UserLoginResult.PASSWORD_WRONG);
        user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);
    }

    //已经登陆情况下再次要求登陆
    private void test4() {
        String email = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX) + "@gmail.com";
        String name = randomCodeCreater.createRandomCode(5, RandomCodeCreater.CodeType.MIX);
        String password = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);
        addUser(email, name, password);
        String sessionId = createSessionId();

        User user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);
        UserLoginResult result = userService.login(sessionId, email, password);
        assertTrue(result == UserLoginResult.SUCCESS);
        result = userService.login(sessionId, email, password);
        assertTrue(result == UserLoginResult.USER_LOGINED);
        user = userService.getCurrentUser(sessionId);
        assertTrue(user != null);
        assertTrue(user.getName().equals(name));
    }

    //提交空值时的验证
    private void test5() {
        String email = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX) + "@gmail.com";
        String name = randomCodeCreater.createRandomCode(5, RandomCodeCreater.CodeType.MIX);
        String password = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);
        addUser(email, name, password);
        String sessionId = createSessionId();

        User user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);
        UserLoginResult result = userService.login(sessionId, null, password);
        assertTrue(result == UserLoginResult.PASSWORD_WRONG);
        user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);

        result = userService.login(sessionId, "", password);
        assertTrue(result == UserLoginResult.PASSWORD_WRONG);
        user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);

        result = userService.login(sessionId, email, null);
        assertTrue(result == UserLoginResult.PASSWORD_WRONG);
        user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);

        result = userService.login(sessionId, email, "");
        assertTrue(result == UserLoginResult.PASSWORD_WRONG);
        user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);

        result = userService.login(sessionId, "", "");
        assertTrue(result == UserLoginResult.PASSWORD_WRONG);
        user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);

        result = userService.login(sessionId, null, null);
        assertTrue(result == UserLoginResult.PASSWORD_WRONG);
        user = userService.getCurrentUser(sessionId);
        assertTrue(user == null);
    }

    public void testProcess() throws InterruptedException {
        test1();
        test2();
        test3();
        test4();
        test5();
    }

    private void stressTest() throws InterruptedException {
        StressTest stressTest = new StressTest();
        stressTest.setNumberOfGroup(10);
        stressTest.setThreadNumberPerGroup(8);

        stressTest.test("myTest", () -> {
            try {
                testProcess();
            } catch (InterruptedException ex) {
                Logger.getLogger(UserServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public static void main(String args[]) throws Exception {
        UserServiceTest userServcieTest = new UserServiceTest();
        userServcieTest.testProcess();
    }
}

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
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.core.impl.EmailSenderImpl;
import robertli.zero.dao.UserRegisterDao;
import robertli.zero.dto.auth.UserEmailRegisterDto;
import robertli.zero.entity.User;
import robertli.zero.entity.UserRegister;
import robertli.zero.service.UserRegisterService.UserRegisterResult;
import robertli.zero.service.UserRegisterService.UserRegisterVerifiyResult;
import robertli.zero.service.UserService.UserLoginResult;
import robertli.zero.test.StressTest;

/**
 *
 * @author Robert Li
 */
public class UserRegisterServiceTest {

    private final UserService userService;
    private final UserRegisterService userRegisterService;
    private final UserRegisterDao userRegisterDao;
    private final RandomCodeCreater randomCodeCreater;

    public UserRegisterServiceTest() {
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

    private UserEmailRegisterDto createRegisterDto(String email, String name, String password, String passwordAgain) {
        UserEmailRegisterDto registerDto = new UserEmailRegisterDto();
        registerDto.setEmail(email);
        registerDto.setName(name);
        registerDto.setPassword(password);
        registerDto.setPasswordAgain(passwordAgain);
        return registerDto;
    }

    //正常注册的情况
    private void testRegister1() {
        String email = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX) + "@gmail.com";
        String name = randomCodeCreater.createRandomCode(5, RandomCodeCreater.CodeType.MIX);
        String password = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);
        UserEmailRegisterDto registerDto = createRegisterDto(email, name, password, password);
        UserRegisterResult result = userRegisterService.registerByEmail(registerDto);

        assertTrue(result == UserRegisterResult.SUBMIT_SUCCESS);
        UserRegister userRegister = userRegisterDao.get(email);
        assertTrue(userRegister != null);
        assertTrue(userRegister.getName().equals(name));
        assertFalse(userRegister.getPassword().equals(password));
        assertTrue(userRegister.getPasswordSalt().length() >= 32);
        assertTrue(userRegister.getVerifiedCode().length() >= 32);

        String verifiedCode = userRegister.getVerifiedCode();
        UserRegisterVerifiyResult verifiyResult = userRegisterService.verifiyRegister(verifiedCode);
        assertTrue(verifiyResult == UserRegisterVerifiyResult.VERIFIY_SUCCESS);

        String SessionId = createSessionId();
        UserLoginResult loginResult = userService.login(SessionId, email, password);
        assertTrue(loginResult == UserLoginResult.SUCCESS);

        User user = userService.getCurrentUser(SessionId);
        assertTrue(user != null);
        assertTrue(user.getName().equals(name));
    }

    //两次密码不一致的情况
    private void testRegister2() {
        String email = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX) + "@gmail.com";
        String name = randomCodeCreater.createRandomCode(5, RandomCodeCreater.CodeType.MIX);
        String password1 = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);
        String password2 = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);
        UserEmailRegisterDto registerDto = createRegisterDto(email, name, password1, password2);
        UserRegisterResult result = userRegisterService.registerByEmail(registerDto);

//        assertTrue(result == UserRegisterResult.PASSWORD_AGAIN_ERROR);
        UserRegister userRegister = userRegisterDao.get(email);
        assertTrue(userRegister == null);
    }

    //注册邮箱包含头尾空格，大小写，点的情况
    private void testRegister3() {
        String part = randomCodeCreater.createRandomCode(9, RandomCodeCreater.CodeType.LOWER_LETTERS);
        String email = " Ty.Ju.d." + part + "@gmail.com  ";
        String name = randomCodeCreater.createRandomCode(5, RandomCodeCreater.CodeType.MIX);
        String password = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);

        UserEmailRegisterDto registerDto = createRegisterDto(email, name, password, password);
        UserRegisterResult result = userRegisterService.registerByEmail(registerDto);
        assertTrue(result == UserRegisterResult.SUBMIT_SUCCESS);

        String label = email.trim();
        String authId = "TyJud" + part.toLowerCase() + "@gmail.com";
        UserRegister userRegister = userRegisterDao.get(authId);
        assertTrue(userRegister != null);
        assertTrue(userRegister.getAuthId().equals(authId));
        assertTrue(userRegister.getAuthLabel().equals(label));
    }

    //同一用户提交两次注册的情况
    private void testRegister4() {
        String email = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX) + "@gmail.com";
        String name = randomCodeCreater.createRandomCode(5, RandomCodeCreater.CodeType.MIX);
        String password = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);
        UserEmailRegisterDto registerDto = createRegisterDto(email, name, password, password);
        UserRegisterResult result = userRegisterService.registerByEmail(registerDto);

        assertTrue(result == UserRegisterResult.SUBMIT_SUCCESS);
        UserRegister userRegister = userRegisterDao.get(email);
        assertTrue(userRegister != null);
        String authId = userRegister.getAuthId();
        String label = userRegister.getAuthLabel();
        String verifiedCode = userRegister.getVerifiedCode();

        registerDto = createRegisterDto(email, name, password, password);
        result = userRegisterService.registerByEmail(registerDto);
        assertTrue(result == UserRegisterResult.REGISTER_EXIST);
        userRegister = userRegisterDao.get(email);
        assertTrue(userRegister != null);
        assertTrue(userRegister.getAuthId().equals(authId));
        assertTrue(userRegister.getAuthLabel().equals(label));
        assertTrue(userRegister.getVerifiedCode().equals(verifiedCode));
    }

    //注册邮箱格式不正确的情况
    private void testRegister5() {
        String email = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX) + "gmail.com";
        String name = randomCodeCreater.createRandomCode(5, RandomCodeCreater.CodeType.MIX);
        String password = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);

        UserEmailRegisterDto registerDto = createRegisterDto(email, name, password, password);
        UserRegisterResult result = userRegisterService.registerByEmail(registerDto);

//        assertTrue(result == UserRegisterResult.EMAIL_FORMAT_ERROR);
        UserRegister userRegister = userRegisterDao.get(email);
        assertTrue(userRegister == null);
    }

    //验证成功后再次提出注册的情况
    private void testRegister6() {
        String email = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX) + "@gmail.com";
        String name = randomCodeCreater.createRandomCode(5, RandomCodeCreater.CodeType.MIX);
        String password = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX);

        UserEmailRegisterDto registerDto = createRegisterDto(email, name, password, password);
        UserRegisterResult result = userRegisterService.registerByEmail(registerDto);

        assertTrue(result == UserRegisterResult.SUBMIT_SUCCESS);
        UserRegister userRegister = userRegisterDao.get(email);
        assertTrue(userRegister != null);

        String verifiedCode = userRegister.getVerifiedCode();
        UserRegisterVerifiyResult verifiyResult = userRegisterService.verifiyRegister(verifiedCode);
        assertTrue(verifiyResult == UserRegisterVerifiyResult.VERIFIY_SUCCESS);

        result = userRegisterService.registerByEmail(registerDto);
        assertTrue(result == UserRegisterResult.USER_EXIST);
    }

    public void test() {
        testRegister1();
//        testRegister2();
        testRegister3();
        testRegister4();
//        testRegister5();
        testRegister6();
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
        UserRegisterServiceTest userRegisterServiceTest = new UserRegisterServiceTest();
        userRegisterServiceTest.stressTest();
    }
}

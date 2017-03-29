/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.entity.User;
import robertli.zero.service.UserService;
import robertli.zero.test.StressTest;

/**
 *
 * @author Robert Li
 */
public class UserDaoTest {

    private final RandomCodeCreater randomCodeCreater;
    private final UserDao userDao;

    public UserDaoTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        userDao = (UserDao) context.getBean("userDao");
        randomCodeCreater = (RandomCodeCreater) context.getBean("randomCodeCreater");
    }

    public void testList() {
//        userDao.list().size();
//        userDao.list(3, 40).size();
//        userDao.list(3).size();
//        userDao.listDesc("id").size();
//        userDao.listDesc("id", 3).size();
//        userDao.listDesc("id", 3, 40).size();
//        System.out.println("last user:" + userDao.getLast("id"));
//        System.out.println("error id user:" + userDao.get(9999999));
//        System.out.println("count user:" + userDao.count());

        List<User> userList = userDao.getUserListByPlatform(UserService.USER_PLATFORM_ADMIN, 0, 100);
        System.out.println("size:" + userList.size());
//        userDao.getUserListByRole(UserService.USER_ROLE_ADMIN_ROOT);

    }

    public void test() {
//        testRegister1();
        testList();
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
        UserDaoTest userDaoTest = new UserDaoTest();
//        userDaoTest.stressTest();
        userDaoTest.test();
    }
}

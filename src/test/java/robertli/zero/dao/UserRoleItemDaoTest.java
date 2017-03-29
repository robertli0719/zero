/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import java.util.Random;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.service.UserService;

/**
 *
 * @author Robert Li
 */
public class UserRoleItemDaoTest {

    private final RandomCodeCreater randomCodeCreater;
    private final UserRoleItemDao userRoleItemDao;
    private final UserRoleDao userRoleDao;
    private final UserDao userDao;
    private final Random random = new Random();

    public UserRoleItemDaoTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        userRoleItemDao = (UserRoleItemDao) context.getBean("userRoleItemDao");
        userRoleDao = (UserRoleDao) context.getBean("userRoleDao");
        userDao = (UserDao) context.getBean("userDao");
        randomCodeCreater = (RandomCodeCreater) context.getBean("randomCodeCreater");
    }

    private void assertRoleExist(int userId, String userRoleName) {
        assertTrue(userRoleItemDao.isExist(userId, userRoleName));
    }

    private void assertRoleNotExist(int userId, String userRoleName) {
        assertFalse(userRoleItemDao.isExist(userId, userRoleName));
    }

    //正常查询插入删除
    private void test1() {
        String userRoleName = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        userRoleDao.addUserRole(userRoleName);

        int userId = userDao.getUserListByPlatform(UserService.USER_PLATFORM_ADMIN, 0, 100).get(0).getId();
        assertRoleNotExist(userId, userRoleName);

        userRoleItemDao.put(userId, userRoleName);
        assertRoleExist(userId, userRoleName);

        userRoleItemDao.delete(userId, userRoleName);
        assertRoleNotExist(userId, userRoleName);
    }

    //重复化查询插入删除
    private void test2() {
        String userRoleName = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        userRoleDao.addUserRole(userRoleName);
        int userId = userDao.getUserListByPlatform(UserService.USER_PLATFORM_ADMIN, 0, 1).get(0).getId();
        assertRoleNotExist(userId, userRoleName);

        userRoleItemDao.put(userId, userRoleName);
        assertRoleExist(userId, userRoleName);

        userRoleItemDao.put(userId, userRoleName);
        assertRoleExist(userId, userRoleName);

        userRoleItemDao.delete(userId, userRoleName);
        assertRoleNotExist(userId, userRoleName);

        userRoleItemDao.delete(userId, userRoleName);
        assertRoleNotExist(userId, userRoleName);
    }

    //单用户多角色支持
    private void test3() {
        String userRoleName1 = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        String userRoleName2 = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        String userRoleName3 = randomCodeCreater.createRandomCode(32, RandomCodeCreater.CodeType.MIX);
        int userId = userDao.getUserListByPlatform(UserService.USER_PLATFORM_ADMIN, 0, 1).get(0).getId();

        userRoleDao.addUserRole(userRoleName1);
        userRoleDao.addUserRole(userRoleName2);
        userRoleDao.addUserRole(userRoleName3);

        assertRoleNotExist(userId, userRoleName1);
        assertRoleNotExist(userId, userRoleName2);
        assertRoleNotExist(userId, userRoleName3);

        userRoleItemDao.put(userId, userRoleName1);
        userRoleItemDao.put(userId, userRoleName2);

        assertRoleExist(userId, userRoleName1);
        assertRoleExist(userId, userRoleName2);
        assertRoleNotExist(userId, userRoleName3);

        userRoleItemDao.delete(userId, userRoleName1);
        userRoleItemDao.put(userId, userRoleName3);

        assertRoleNotExist(userId, userRoleName1);
        assertRoleExist(userId, userRoleName2);
        assertRoleExist(userId, userRoleName3);
    }

    public void test() {
        test1();
        test2();
        test3();
    }

    public static void main(String args[]) {
        UserRoleItemDaoTest test = new UserRoleItemDaoTest();
        test.test();
    }

}

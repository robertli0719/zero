/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.entity.User;
import robertli.zero.dto.SearchResult;
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

    public void testRegister1() {
        SearchResult<User> result = userDao.paging(2, 10);
        System.out.println(result.getCount());
        System.out.println(result.getMax());
        System.out.println(result.getPageId());
        System.out.println(result.getPageSize());
        System.out.println(result.getStart());
        for(User user:result.getList()){
            System.out.println(user.getId()+"\t"+user.getName());
        }

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
        UserDaoTest userDaoTest = new UserDaoTest();
        userDaoTest.stressTest();
        //userDaoTest.test();
    }
}

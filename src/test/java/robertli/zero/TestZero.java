/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.entity.TestRecord;
import robertli.zero.service.TestService;
import static org.junit.Assert.*;
import robertli.zero.dao.TestRecodeDao;

/**
 *
 * @author Robert Li
 */
public class TestZero {

    private List<TestRecord> createRandTestList() {
        List testRecodeList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String key = UUID.randomUUID().toString();
            TestRecord test = new TestRecord();
            test.setKeyname(key);
            testRecodeList.add(test);
        }
        return testRecodeList;
    }

    private boolean recodeListEquals(List<TestRecord> aList, List<TestRecord> bList) {
        if (aList.size() != bList.size()) {
            return false;
        }
        HashSet<String> set = new HashSet<>();
        for (TestRecord t : aList) {
            set.add(t.getKeyname());
        }
        for (TestRecord t : bList) {
            set.remove(t.getKeyname());
        }
        return set.isEmpty();
    }

    private void shouldSuccess(List<TestRecord> testRecodeList, List<TestRecord> resultList) {
        assertFalse(resultList.isEmpty());
        assertTrue(recodeListEquals(testRecodeList, resultList));
    }

    private void shouldFail(List<TestRecord> resultList) {
        assertTrue(resultList.isEmpty());
    }

    private void testA(TestService testService, TestRecodeDao testRecodeDao) {
        List<TestRecord> testRecodeList = createRandTestList();

        testService.clearAll();
        testRecodeDao.testA(testRecodeList);
        List<TestRecord> resultList = testService.getList();

        shouldSuccess(testRecodeList, resultList);
    }

    private void testB(TestService testService, TestRecodeDao testRecodeDao) {
        List<TestRecord> testRecodeList = createRandTestList();

        testService.clearAll();
        boolean hasException = false;
        try {
            testRecodeDao.testB(testRecodeList);
        } catch (RuntimeException re) {
            hasException = true;
        }
        List<TestRecord> resultList = testService.getList();

        shouldFail(resultList);
        assertTrue(hasException);
    }

    private void test1(TestService testService) {
        List<TestRecord> testRecodeList = createRandTestList();

        testService.clearAll();
        testService.test1(testRecodeList);
        List<TestRecord> resultList = testService.getList();

        shouldSuccess(testRecodeList, resultList);
    }

    private void test2(TestService testService) {
        List<TestRecord> testRecodeList = createRandTestList();

        testService.clearAll();
        boolean hasException = false;
        try {
            testService.test2(testRecodeList);
        } catch (RuntimeException re) {
            hasException = true;
        }
        List<TestRecord> resultList = testService.getList();

        shouldFail(resultList);
        assertTrue(hasException);
    }

    private void test3(TestService testService) {
        List<TestRecord> testRecodeList = createRandTestList();

        testService.clearAll();
        testService.test3(testRecodeList);
        List<TestRecord> resultList = testService.getList();

        shouldSuccess(testRecodeList, resultList);
    }

    private void test4(TestService testService) {
        List<TestRecord> testRecodeList = createRandTestList();

        testService.clearAll();
        boolean hasException = false;
        try {
            testService.test4(testRecodeList);
        } catch (RuntimeException re) {
            hasException = true;
        }
        List<TestRecord> resultList = testService.getList();

        shouldFail(resultList);
        assertTrue(hasException);
    }

    private void test5(TestService testService) {
        List<TestRecord> testRecodeList = createRandTestList();

        testService.clearAll();
        boolean hasException = false;
        try {
            testService.test5(testRecodeList);
        } catch (RuntimeException re) {
            hasException = true;
        }
        List<TestRecord> resultList = testService.getList();

        shouldFail(resultList);
        assertTrue(hasException);
    }

    private void test6(TestService testService) {
        List<TestRecord> testRecodeList = createRandTestList();

        testService.clearAll();
        boolean hasException = false;
        try {
            testService.test6(testRecodeList);
        } catch (RuntimeException re) {
            hasException = true;
        }
        List<TestRecord> resultList = testService.getList();

        shouldFail(resultList);
        assertTrue(hasException);
    }

    private void test7(TestService testService) {
        List<TestRecord> testRecodeList = createRandTestList();

        testService.clearAll();
        boolean hasException = false;
        try {
            testService.test7(testRecodeList);
        } catch (RuntimeException re) {
            hasException = true;
        }
        List<TestRecord> resultList = testService.getList();

        shouldFail(resultList);
        assertTrue(hasException);
    }

    private void test8(TestService testService) {
        List<TestRecord> testRecodeList = createRandTestList();

        testService.clearAll();
        boolean hasException = false;
        try {
            testService.test8(testRecodeList);
        } catch (RuntimeException re) {
            hasException = true;
        }
        List<TestRecord> resultList = testService.getList();

        shouldFail(resultList);
        assertTrue(hasException);
    }

    public void testSpringTransaction() {
        System.out.println("Start Test Spring Transaction");

        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        TestService testService = (TestService) context.getBean("testService");
        TestRecodeDao testRecodeDao = (TestRecodeDao) context.getBean("testRecodeDao");

        testService.clearAll();
        assertTrue(testService.getList().isEmpty());

        //Dao 正常存储
        testA(testService, testRecodeDao);

        //Dao存储后测试rollback
        testB(testService, testRecodeDao);

        //service正常存储
        test1(testService);

        //service存储后测试rollback
        test2(testService);

        //service嵌套test1正常存储
        test3(testService);

        //service嵌套test1，再抛异常，测试rollback
        test4(testService);

        //service嵌套一个会rollback的service方法，测试rollback
        test5(testService);

        //service 调用Dao成功存储后自身抛出异常，测试rollback
        test6(testService);

        //service 调用Dao成功存储后，调用另一个Dao方法抛出异常，测试rollback
        test7(testService);

        //service 自身存储成功后，调用Dao的方法抛出异常，测试rollback
        test8(testService);

        //authService.login(sessionId, userAuthId, orginealPassword);
        System.out.println("Test start");
    }

    public static void main(String args[]) {
        TestZero t = new TestZero();

        t.testSpringTransaction();
    }
}

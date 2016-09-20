/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.entity.TestRecode;
import robertli.zero.service.TestService;
import robertli.zero.dao.TestRecodeDao;

@Component("testService")
public class TestServiceImpl implements TestService {

    @Resource
    private TestRecodeDao testDao;

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public void clearAll() {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from TestRecode").executeUpdate();
    }

    @Override
    public List<TestRecode> getList() {
        return testDao.list();
    }

    @Override
    public void save(TestRecode test) {
        testDao.save(test);
    }

    @Override
    public void save(List<TestRecode> testList) {
        for (TestRecode test : testList) {
            testDao.save(test);
        }
    }

    @Override
    public void get(String key) {
        testDao.get(key);
    }

    @Override
    public void deleteByKey(String key) {
        testDao.deleteById(key);
    }

    @Override
    public void test1(List<TestRecode> testList) {
        Session session = sessionFactory.getCurrentSession();
        for (TestRecode test : testList) {
            session.save(test);
        }
    }

    @Override
    public void test2(List<TestRecode> testList) {
        Session session = sessionFactory.getCurrentSession();
        for (TestRecode test : testList) {
            session.save(test);
        }
        TestRecode testRecode = new TestRecode();
        session.save(testRecode);//save null for throw Runtime Exception
    }

    @Override
    public void test3(List<TestRecode> testList) {
        test1(testList);
    }

    @Override
    public void test4(List<TestRecode> testList) {
        test1(testList);
        Session session = sessionFactory.getCurrentSession();
        TestRecode testRecode = new TestRecode();
        session.save(testRecode);//save null for throw Runtime Exception
    }

    @Override
    public void test5(List<TestRecode> testList) {
        test2(testList);
    }

    @Override
    public void test6(List<TestRecode> testList) {
        testDao.testA(testList);
        throw new RuntimeException();
    }

    @Override
    public void test7(List<TestRecode> testList) {
        testDao.testA(testList);
        testDao.testB(testList);
    }

    @Override
    public void test8(List<TestRecode> testList) {
        test1(testList);
        testDao.testB(testList);
    }

}

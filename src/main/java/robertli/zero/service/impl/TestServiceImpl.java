/*
 * The MIT License
 *
 * Copyright 2016 Robert Li.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package robertli.zero.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.entity.TestRecord;
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
    public List<TestRecord> getList() {
        return testDao.list();
    }

    @Override
    public void save(TestRecord test) {
        testDao.save(test);
    }

    @Override
    public void save(List<TestRecord> testList) {
        for (TestRecord test : testList) {
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
    public void test1(List<TestRecord> testList) {
        Session session = sessionFactory.getCurrentSession();
        for (TestRecord test : testList) {
            session.save(test);
        }
    }

    @Override
    public void test2(List<TestRecord> testList) {
        Session session = sessionFactory.getCurrentSession();
        for (TestRecord test : testList) {
            session.save(test);
        }
        TestRecord testRecode = new TestRecord();
        session.save(testRecode);//save null for throw Runtime Exception
    }

    @Override
    public void test3(List<TestRecord> testList) {
        test1(testList);
    }

    @Override
    public void test4(List<TestRecord> testList) {
        test1(testList);
        Session session = sessionFactory.getCurrentSession();
        TestRecord testRecode = new TestRecord();
        session.save(testRecode);//save null for throw Runtime Exception
    }

    @Override
    public void test5(List<TestRecord> testList) {
        test2(testList);
    }

    @Override
    public void test6(List<TestRecord> testList) {
        testDao.testA(testList);
        throw new RuntimeException();
    }

    @Override
    public void test7(List<TestRecord> testList) {
        testDao.testA(testList);
        testDao.testB(testList);
    }

    @Override
    public void test8(List<TestRecord> testList) {
        test1(testList);
        testDao.testB(testList);
    }

}

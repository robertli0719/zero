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
package robertli.zero.dao.impl;

import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.entity.TestRecord;
import robertli.zero.dao.TestRecodeDao;

/**
 * JUnit can use this TestRecodeDao for system self-checking
 *
 * @version 1.0 2016-09-19
 * @author Robert Li
 */
@Component("testRecodeDao")
public class TestRecodeDaoImpl extends GenericHibernateDao<TestRecord, String> implements TestRecodeDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public void testA(List<TestRecord> testRecodeList) {
        Session session = sessionFactory.getCurrentSession();
        for (TestRecord test : testRecodeList) {
            session.save(test);
        }
    }

    @Override
    public void testB(List<TestRecord> testRecodeList) {
        Session session = sessionFactory.getCurrentSession();
        for (TestRecord test : testRecodeList) {
            session.save(test);
        }
        throw new RuntimeException();
    }

}

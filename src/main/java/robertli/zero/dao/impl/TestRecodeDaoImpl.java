/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

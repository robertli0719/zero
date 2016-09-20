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
import robertli.zero.entity.TestRecode;
import robertli.zero.dao.TestRecodeDao;

/**
 * JUnit can use this TestRecodeDao for system self-checking
 *
 * @version 1.0 2016-09-19
 * @author Robert Li
 */
@Component("testRecodeDao")
public class TestRecodeDaoImpl extends GenericHibernateDao<TestRecode, String> implements TestRecodeDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public void testA(List<TestRecode> testRecodeList) {
        Session session = sessionFactory.getCurrentSession();
        for (TestRecode test : testRecodeList) {
            session.save(test);
        }
    }

    @Override
    public void testB(List<TestRecode> testRecodeList) {
        Session session = sessionFactory.getCurrentSession();
        for (TestRecode test : testRecodeList) {
            session.save(test);
        }
        throw new RuntimeException();
    }

}

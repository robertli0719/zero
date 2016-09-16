/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero;

import javax.sql.DataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Robert Li
 */
public class HibernateTest {

    public static void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        SessionFactory sessionFactory = (SessionFactory) context.getBean("sessionFactory");
        
        Session session = sessionFactory.openSession();
        
        session.close();
    }

    public static void main(String args[]) {
        test();
    }
}

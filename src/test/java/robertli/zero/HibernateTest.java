/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero;

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

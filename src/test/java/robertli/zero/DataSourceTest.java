/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Robert Li
 */
public class DataSourceTest {

    public static void testDataSource() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

        DataSource dataSource = (DataSource) context.getBean("dataSource");
        System.out.println("dataSource:");
        System.out.println(dataSource);
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            //statement.execute("insert into users(id,name)values(2,'zhangsan2');");
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DataSourceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) {
        testDataSource();
    }
}

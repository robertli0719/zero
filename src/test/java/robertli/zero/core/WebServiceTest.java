/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.core;

import java.util.UUID;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Robert Li
 */
public class WebServiceTest {
    
    private static void test() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        WebService webService = (WebService) context.getBean("webService");
        
        String str = webService.get("https://www.google.ca");
        System.out.println(str);
        

    }

    public static void main(String args[]) throws Exception {
        test();
    }
}

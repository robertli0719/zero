/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

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

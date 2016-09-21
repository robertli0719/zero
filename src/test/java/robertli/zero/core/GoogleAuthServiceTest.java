/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.core;

import java.io.IOException;
import java.util.Map;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Robert Li
 */
public class GoogleAuthServiceTest {

    public void test() throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        GoogleAuthService service = (GoogleAuthService) context.getBean("googleAuthService");

        String token = "sdf";
        Map<String, String> map = service.getInfo(token);
        for (String key : map.keySet()) {
            String val = map.get(key);
            System.out.println(key + "\t" + val);
        }
    }

    public static void main(String args[]) throws IOException {
        GoogleAuthServiceTest t = new GoogleAuthServiceTest();
        t.test();
    }
}

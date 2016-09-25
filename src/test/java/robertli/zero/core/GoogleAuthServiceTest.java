/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
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

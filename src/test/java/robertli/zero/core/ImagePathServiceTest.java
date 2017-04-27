/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

import javax.annotation.Resource;
import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Robert Li
 */
@Component("imagePathServiceTest")
public class ImagePathServiceTest {

    @Resource
    private PathService pathService;

    private void testNull() {
        final String uuid = pathService.pickImageId(null);
        assertTrue(uuid == null);
//        testNull
    }

    public void run() {
        testNull();
//        final String uuid = UUID.randomUUID().toString();
    }

    public static void main(String args[]) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        ImagePathServiceTest test = (ImagePathServiceTest) context.getBean("imagePathServiceTest");
        test.run();
    }
}

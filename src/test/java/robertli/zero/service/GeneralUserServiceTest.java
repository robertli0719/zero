/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import javax.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author Robert Li
 */
@Component("generalUserServiceTest")
public class GeneralUserServiceTest {

    @Resource
    private GeneralUserService generalUserService;

    public void run() {
    }

    public static void main(String args[]) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        GeneralUserServiceTest test = (GeneralUserServiceTest) context.getBean("generalUserServiceTest");
        test.run();
    }
}

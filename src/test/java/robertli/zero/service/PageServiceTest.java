/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.entity.PageCategory;

/**
 *
 * @author Robert Li
 */
public class PageServiceTest {

    private final PageService pageService;
    private final RandomCodeCreater randomCodeCreater;
    private final Random rand;

    public PageServiceTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        pageService = (PageService) context.getBean("pageService");
        randomCodeCreater = (RandomCodeCreater) context.getBean("randomCodeCreater");
        rand = new Random();
    }

    private PageCategory foundCateogry(String name) {
        List<PageCategory> list = pageService.listCategory();
        assertTrue(list != null);
        for (PageCategory pc : list) {
            if (pc.getName().equals(name)) {
                return pc;
            }
        }
        return null;
    }

    //页面类型的增删查
    private void test1() {
        List<PageCategory> list = pageService.listCategory();
        assertTrue(list != null);

        String name = randomCodeCreater.createRandomCode(20, RandomCodeCreater.CodeType.MIX);
        String description = randomCodeCreater.createRandomCode(20, RandomCodeCreater.CodeType.MIX);
        boolean fail = pageService.addCategory(name, description);
        assertFalse(fail);

        PageCategory pc = foundCateogry(name);
        assertTrue(pc != null);
        assertTrue(pc.getName().equals(name));
        assertTrue(pc.getDescription().equals(description));

        fail = pageService.removeCategory(name);
        assertFalse(fail);
        pc = foundCateogry(name);
        assertTrue(pc == null);
    }

    public void test() {
        test1();
    }

    public static void main(String args[]) {
        PageServiceTest pageServiceTest = new PageServiceTest();
        pageServiceTest.test();
    }

}

/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.entity.Page;
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

    private void makeTestCateogry() {
        for (PageCategory pc : pageService.listCategory()) {
            if (pc.getName().equals("test")) {
                return;
            }
        }
        pageService.addCategory("test", "for testing");
    }

    private Page getPageByTitle(String category, String title) {
        List<Page> pageList = pageService.listByCategory(category);
        for (Page page : pageList) {
            if (page.getTitle().equals(title)) {
                return page;
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

    //插入unicode
    private void test2() throws UnsupportedEncodingException {
        makeTestCateogry();
        String title = randomCodeCreater.createRandomCode(100, RandomCodeCreater.CodeType.MIX);
        System.out.println(title.length());

        String content = "hello,world~!😍\u1F60D";
        pageService.addPage("test", title, "this is a test page", content);
        Page page = getPageByTitle("test", title);
        assertTrue(page != null);
        assertTrue(page.getContent().equals(content));
    }

    public void test() throws UnsupportedEncodingException {
        test1();
        test2();
    }

    public static void main(String args[]) throws UnsupportedEncodingException {
        PageServiceTest pageServiceTest = new PageServiceTest();
        pageServiceTest.test();
//
//        String str = "hello,world~!\u1F60D";
//        String newString = URLEncoder.encode(str, "UTF-8");
//        System.out.println(newString);
//
//        String de = URLDecoder.decode(newString, "UTF-8");
//        System.out.println(de);
    }

}

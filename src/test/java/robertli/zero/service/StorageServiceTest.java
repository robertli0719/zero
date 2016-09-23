/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Robert Li
 */
public class StorageServiceTest {

    private StorageService storageService;

    public StorageServiceTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        storageService = (StorageService) context.getBean("storageService");
    }

    public String testSaveFile(String str) {
        String filename = "hello.txt";
        String type = "txt";
        byte[] data = str.getBytes();
        return storageService.add(filename, type, data);
    }

    public void showFile(String uuid) {
        byte[] data = storageService.get(uuid);
        if (data == null) {
            System.out.println("file:" + uuid + " is null");
            return;
        }
        String result = new String(data);
        System.out.println(result);
    }

    public void test() {
        String file1 = testSaveFile("hello,world~!");
        String file2 = testSaveFile("test test test");

        storageService.delete(file2);
        String file3 = testSaveFile("a b c d e f g");
        showFile(file1);
        showFile(file2);
        showFile(file3);

    }

    public static void main(String args[]) {
        StorageServiceTest test = new StorageServiceTest();
        test.test();
    }
}

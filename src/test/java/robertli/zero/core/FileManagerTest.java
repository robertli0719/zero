/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.core;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Robert Li
 */
public class FileManagerTest {

    private static void test() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        FileManager fileManager = (FileManager) context.getBean("fileManager");

        String uuid = UUID.randomUUID().toString();
        String myData = "Hello,world~!";
        fileManager.write(uuid, myData.getBytes());
        String str = new String(fileManager.read(uuid));

        if (myData.equals(str) == false) {
            throw new Exception("Fail to get right data");
        }
        fileManager.delete(uuid);
    }

    public static void main(String args[]) throws Exception {
        test();
    }
}

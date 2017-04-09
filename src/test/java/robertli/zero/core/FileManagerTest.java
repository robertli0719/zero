/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
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

        final String uuid = UUID.randomUUID().toString();
        final String myData = "Hello,world~!";
        final byte data[] = myData.getBytes();
        final ByteArrayInputStream bain = new ByteArrayInputStream(data);

        fileManager.write(uuid, bain, data.length);
        final InputStream in = fileManager.getInputStream(uuid, 0);
        final byte[] buffer = new byte[myData.getBytes().length];
        in.read(buffer);
        final String str = new String(buffer);

        if (myData.equals(str) == false) {
            throw new Exception("Fail to get right data");
        }
        fileManager.delete(uuid);
    }

    public static void main(String args[]) throws Exception {
        test();
    }
}

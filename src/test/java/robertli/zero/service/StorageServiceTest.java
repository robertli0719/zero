/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.test.StressTest;

/**
 *
 * @author Robert Li
 */
public class StorageServiceTest {

    private final StorageService storageService;
    private final RandomCodeCreater randomCodeCreater;
    private final Random rand;

    private StorageServiceTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        storageService = (StorageService) context.getBean("storageService");
        randomCodeCreater = (RandomCodeCreater) context.getBean("randomCodeCreater");
        rand = new Random();
    }

    private String makeRandType() {
        double val = rand.nextDouble();
        if (val < 0.3) {
            return "jpg";
        } else if (val < 0.5) {
            return "png";
        } else if (val < 0.7) {
            return "bmp";
        }
        return "bin";
    }

    private void testProcess() throws InterruptedException, IOException {
        final int FILE_SIZE = rand.nextInt(1024 * 1024 * 10);// file size between 0~10M
        final byte data[] = new byte[FILE_SIZE];
        final String type = makeRandType();
        final String name = randomCodeCreater.createRandomCode(5, RandomCodeCreater.CodeType.MIX) + "." + type;
        rand.nextBytes(data);

        Thread.sleep(rand.nextInt(1000));
        String uuid = storageService.register(name, type);
        storageService.store(uuid, data);
        
        while (rand.nextDouble() > 0.2) {
            if(rand.nextDouble()<0.1){
                storageService.clean();
            }
            byte result[] = storageService.get(uuid);
            if (Arrays.equals(data, result) == false) {
                throw new RuntimeException("result is wrong!");
            }
        }
        Thread.sleep(rand.nextInt(1000));
        storageService.delete(uuid);
    }

    private void stressTest() throws InterruptedException {
        StressTest stressTest = new StressTest();
        stressTest.setNumberOfGroup(10);
        stressTest.setThreadNumberPerGroup(8);

        stressTest.test("myTest", () -> {
            try {
                testProcess();
            } catch (InterruptedException ex) {
                Logger.getLogger(StorageServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(StorageServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public static void main(String args[]) throws InterruptedException {
        StorageServiceTest test = new StorageServiceTest();
        //test.testProcess();
        test.stressTest();
    }
}

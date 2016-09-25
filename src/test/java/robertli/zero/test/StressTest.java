/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.test;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * I make this tool for testing my program performance in multi-threading
 * situation.
 *
 * @version 1.0 2016-09-22
 * @author Robert Li
 */
public class StressTest {

    private int threadNumberPerGroup = 8;
    private int numberOfGroup = 1000;

    public void setThreadNumberPerGroup(int threadNumberPerGroup) {
        this.threadNumberPerGroup = threadNumberPerGroup;
    }

    public void setNumberOfGroup(int numberOfGroup) {
        this.numberOfGroup = numberOfGroup;
    }

    private void groupTest(Runnable runnable) throws InterruptedException {

        Thread[] threadArray = new Thread[threadNumberPerGroup];
        for (int i = 0; i < threadArray.length; i++) {
            threadArray[i] = new Thread(runnable);
        }
        for (Thread th : threadArray) {
            th.start();
        }
        for (Thread th : threadArray) {
            th.join();
        }

    }

    public void test(String testName, Runnable runnable) throws InterruptedException {
        System.out.println("\n\nStart stress test:" + testName);
        System.out.println("thread per test group:" + threadNumberPerGroup);
        System.out.println("group number:" + numberOfGroup);

        for (int i = 1; i <= numberOfGroup; i++) {
            System.out.println("start test group:" + i);
            groupTest(runnable);
        }
        System.out.println("\n[stress test is complete.]\n");
    }

    public static void main(String args[]) throws InterruptedException {
        StressTest test = new StressTest();

        test.test("myTest", () -> {
            for (int i = 0; i < 10; i++) {
                System.out.print(i);
                Random rand = new Random();
                int r = rand.nextInt(100);
                try {
                    Thread.sleep(r);
                } catch (InterruptedException ex) {
                    Logger.getLogger(StressTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }
}

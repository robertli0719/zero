/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

import java.util.Date;
import robertli.zero.core.impl.SmsSenderAwsImpl;

/**
 *
 * @author Robert Li
 */
public class SmsSenderTest {

    private void test() {
        final SmsSender smsSender = new SmsSenderAwsImpl();
        final String phoneNumber = "+16043000134";
        final String message = "Hello, World~!\n" + (new Date());
//        smsSender.send(phoneNumber, message);

        for (int i = 0; i < 2; i++) {
            smsSender.send(phoneNumber, "test:" + i);
        }
    }

    public static void main(String args[]) {
        SmsSenderTest t = new SmsSenderTest();
        t.test();
    }
}

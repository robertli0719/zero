/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.tool;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;

/**
 *
 * @version 1.0 2017-01-26
 * @author Robert Li
 */
public class Prime {

    public static BigInteger generate(int bits) {
        SecureRandom random = new SecureRandom();
        random.setSeed(new Date().getTime());
        BigInteger num;
        do {
            num = BigInteger.probablePrime(bits, random);
        } while (!num.isProbablePrime(1));
        return num;
    }

    public static void main(String args[]) {

        System.out.println(generate(31));
    }
}

/*
 * The MIT License
 *
 * Copyright 2016 Robert Li.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package robertli.zero.tool;

/**
 * Luhn algorithm<br>
 * This one can be used for checking credit card or any number based on Luhn.
 *
 * @version 1.1 2016-08-21
 * @author robert
 */
public class Luhn {

    private static int BITS_ARRAY[] = {0, 2, 4, 6, 8, 1, 3, 5, 7, 9};

    /**
     * verify if a luhn code is correct.
     *
     * @param number a code which is based on Luhn
     * @return true if correct
     */
    public static boolean verify(long number) {
        if (number < 10) {
            return false;
        }
        int sum = 0, b = 0;
        while (number != 0) {
            int n = (int) (number % 10);
            sum += b++ % 2 == 0 ? n : BITS_ARRAY[n];
            number /= 10;
        }
        return sum % 10 == 0;
    }

    /**
     * Count the check bit for a common number
     *
     * @param number the number before check bit
     * @return the check bit for the number
     */
    public static int countCheckDigit(long number) {
        int sum = 0, b = 1;
        while (number != 0) {
            int n = (int) (number % 10);
            sum += b++ % 2 == 0 ? n : BITS_ARRAY[n];
            number /= 10;
        }
        return sum % 10 == 0 ? 0 : 10 - sum % 10;
    }

    /**
     * Add a Luhn check bit on the end of a common number
     *
     * @param number
     * @return
     */
    public static long appendCheckDigit(long number) {
        int checkDigit = countCheckDigit(number);
        return number * 10 + checkDigit;
    }

    public static void main(String args[]) {
        boolean result = Luhn.verify(4519011328206481L);
        long v=Luhn.appendCheckDigit(451901132820448L);
        System.out.println(v);
    }

}

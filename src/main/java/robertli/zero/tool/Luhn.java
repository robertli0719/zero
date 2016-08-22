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
        System.out.println(result);
    }

}

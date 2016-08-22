package robertli.zero.tool;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * Design for AES encryption
 *
 * @version v1.1 2016-08-21
 * @author Robert Li
 */
public class AES {

    public static String createKey() {
        KeyGenerator keygen;
        try {
            keygen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
        SecureRandom random = new SecureRandom();
        keygen.init(random);
        SecretKey key = keygen.generateKey();
        byte key_arr[] = key.getEncoded();

        return DatatypeConverter.printBase64Binary(key_arr);
    }

    public static String encrypt(String line, String key_str) {
        byte[] result_byte = null;
        byte key_arr[] = DatatypeConverter.parseBase64Binary(key_str);
        SecretKey key = new SecretKeySpec(key_arr, 0, key_arr.length, "AES");
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            result_byte = cipher.doFinal(line.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            System.err.println("fail");
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            System.err.println("fail");
        }
        return DatatypeConverter.printBase64Binary(result_byte);
    }

    public static String decrypt(String secret, String key_str) {
        byte[] result_byte = null;
        byte key_arr[] = DatatypeConverter.parseBase64Binary(key_str);
        SecretKey key = new SecretKeySpec(key_arr, 0, key_arr.length, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] arr = DatatypeConverter.parseBase64Binary(secret);
            result_byte = cipher.doFinal(arr);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException ex) {
            System.err.println("fail");
        } catch (IllegalBlockSizeException | BadPaddingException ex) {
            System.err.println("fail");
        }

        return new String(result_byte);
    }

    public static void main(String args[]) throws NoSuchAlgorithmException {
        String str = "hello world~!";
        String key = AES.createKey();
        String secret = AES.encrypt(str, key);
        String final_str = AES.decrypt(secret, key);

        System.out.println(str);
        System.out.println(key);
        System.out.println(secret);
        System.out.println(final_str);
    }
}

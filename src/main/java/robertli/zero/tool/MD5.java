package robertli.zero.tool;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MD5 counter
 *
 * @version 1.0 2016-08-21
 * @author RobertLi
 */
public class MD5 {

    public static String count(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes(Charset.forName("UTF-8")));
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            content = sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MD5.class.getName()).log(Level.SEVERE, null, ex);
        }
        return content;
    }

}

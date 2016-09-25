/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This service is design for the common use of check or preprocess user's input
 * field.<br>
 *
 * @version 1.0 2016-08-11
 * @author Robert Li
 */
public class ValidationTool {

    /**
     * validate the email address format
     *
     * @param email the user's input from email field
     * @return true when the email address is valid
     */
    public static boolean checkEmail(String email) {
        if (email == null) {
            return false;
        } else if (email.isEmpty()) {
            return false;
        }
        String check = "^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-_[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches() != false;
    }

    /**
     * preprocess the input of user for email address. remove the space in head
     * and end. change the string to lower case. remove any point before @.
     *
     * @param email the user's input from email field
     * @return the email string after preprocess
     */
    public static String preprocessEmail(String email) {
        email = (email == null) ? "" : email.trim().toLowerCase();
        String part[] = email.split("@");
        if (part.length == 2) {
            return part[0].replaceAll("\\.", "") + "@" + part[1];
        }
        return email;
    }

}

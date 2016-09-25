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

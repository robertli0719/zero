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
package robertli.zero.service;

/**
 * User can use this service to reset their password<br>
 * There are three ways to reset user's password.<br>
 *
 * 1.after user login, the user can reset own password<br>
 * 2.user can use a token to reset password<br>
 * 3.user can use a token to ask system to create a random password<br>
 *
 * no matter if the user has login, the user can ask a token for resetting
 * password. The token is single use, and each token has a one day long life.
 * user can own more than one token at the same time.
 *
 * @version 1.0 2016-09-19
 * @author Robert Li
 */
public interface UserPasswordResetService {

    public static enum CreatePasswordResetTokenResult {
        SUCCESS,
        EMAIL_FORMAT_ERROR,
        USER_NOT_FOUND,
        DATABASE_EXCEPTION,
        SMS_SEND_FAIL
    }

    /**
     * Users use this function to ask a token for resetting their password.<br>
     * This function will create a new token for the user no matter if the user
     * has any other tokens. Users can keep more than one tokens at the same
     * time. Each token is single use and has one day useful life.
     *
     * @param email
     * @return A status set of running result.
     */
    public CreatePasswordResetTokenResult createPasswordResetToken(String email);

    public static enum ResetPasswordResult {
        SUCCESS,
        PASSWORD_AGAIN_ERROR,
        PASSWORD_SHORTER_THAN_8,
        CODE_WORING,
        DATABASE_EXCEPTION,
    }

    /**
     * User use their own token to reset password
     *
     * @param tokenCode the token for reset password
     * @param orginealPassword the input of new password
     * @param passwordAgain user should type the password twice
     * @return A status set of running result.
     */
    public ResetPasswordResult resetPassword(String tokenCode, String orginealPassword, String passwordAgain);
}

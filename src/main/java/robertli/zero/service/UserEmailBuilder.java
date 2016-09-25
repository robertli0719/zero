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

import robertli.zero.core.EmailMessage;

/**
 * UserEmailBuilder can build EmailMessage for all services about user.
 *
 * @version 1.0 2016-09-19
 * @author Robert Li
 */
public interface UserEmailBuilder {

    /**
     * The user, who just submit the information by RegisterByEmail(), should
     * get a verification email. The email includes the verification code which
     * is necessary for finish the register.<br>
     *
     * @param email email address
     * @param name user's name
     * @param verifiedCode the code for verify this user's email
     * @return EmailMessage
     */
    public EmailMessage buildUserRegisterVerificationEmail(String email, String name, String verifiedCode);

    /**
     *
     * @param email email address
     * @param name user's name
     * @param token the token for ask reset password
     * @return EmailMessage
     */
    public EmailMessage buildPasswordResetTokenEmail(String email, String name, String token);
}

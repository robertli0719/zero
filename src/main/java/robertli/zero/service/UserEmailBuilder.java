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

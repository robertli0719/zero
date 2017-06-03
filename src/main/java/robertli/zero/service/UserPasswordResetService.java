/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
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
 * @version 1.0.1 2017-06-02
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
     * @param email the email address which need be reseted the password
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
     * @param originalPassword the input of new password
     * @param passwordAgain user should type the password twice
     * @return A status set of running result.
     */
    public ResetPasswordResult resetPassword(String tokenCode, String originalPassword, String passwordAgain);
}

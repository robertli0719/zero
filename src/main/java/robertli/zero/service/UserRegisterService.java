package robertli.zero.service;

/**
 * This service is design for users to register the system.<br>
 * In my plan, users can register by email, telephone, or any other ways,but my
 * code currently just support email register so far.<br>
 * When a user want to register, he or she should use registerByEmail() to
 * submit the user's information. Then, the system will use the user's email
 * address to send a validation email to validate if this user is the owner of
 * the email.The email will includes a verified code, so that the user can use
 * the code to implement verifiyRegister() to finish the register.
 *
 * @version 1.0.2 2016-09-19
 * @author Robert Li
 */
public interface UserRegisterService {

    /**
     * The user, who just submit the information by RegisterByEmail(), can use
     * this function to ask a verification email. The email includes the
     * verification code which is necessary for finish the register.<br>
     * This function will be implements automatically within RegisterByEmail(),
     * but users can use this function to ask the email again if they can't get
     * the last email.
     *
     * @param email
     * @return ture when fail
     */
    public boolean sendRegisterVerificationEmail(String email);

    /**
     * This function has not finish
     *
     * @param telephone the phone number
     * @return ture when fail
     */
    public boolean sendRegisterVerificationSMS(String telephone);

    public static enum UserRegisterResult {
        SUBMIT_SUCCESS,
        USER_EXIST,
        REGISTER_EXIST,
        EMAIL_SEND_FAIL,
        SMS_SEND_FAIL,
        EMAIL_FORMAT_ERROR,
        PASSWORD_AGAIN_ERROR,
        PASSWORD_SHORTER_THAN_8,
        NO_NAME_ERROR,
        DATABASE_EXCEPTION
    }

    /**
     * User use their own email to register.<br>
     * users use this function to submit the information of their register.Than,
     * the system will save the info to database and send a validation email to
     * the user with a verification code.<br>
     * The reason why I use the word 'orginealPassword' rather than 'password'
     * is that the field 'password' should be with MD5. In other words, the
     * password is protected by MD5, and original password is the real one.
     *
     * @param email the email address
     * @param originalPassword the real password which inputs by user
     * @param passwordAgain user should type the password twice
     * @param name the name which will show on pages after user login
     * @return a status set of running result
     */
    public UserRegisterResult registerByEmail(String email, String originalPassword, String passwordAgain, String name);

    /**
     * This function has not finish
     *
     * @param telephone the phone number
     * @param orginealPassword the real password which inputs by user
     * @param passwordAgain user should type the password twice
     * @param nickname the name which will show on pages after user login
     * @return a status set of running result
     */
    public UserRegisterResult registerByTelephone(String telephone, String orginealPassword, String passwordAgain, String nickname);

    public static enum UserRegisterVerifiyResult {
        DATABASE_EXCEPTION,
        VERIFIY_SUCCESS,
        VERIFIED_CODE,
        VERIFIY_FAIL
    }

    /**
     * Users use this function to finish their register after they get their
     * verified code in their validation email.
     *
     * @param verifiedCode
     * @return a status set of running result
     */
    public UserRegisterVerifiyResult verifiyRegister(String verifiedCode);
}

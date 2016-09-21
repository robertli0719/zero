/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.service.impl;

import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.EmailMessage;
import robertli.zero.core.EmailSender;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.core.SecurityService;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.dao.UserDao;
import robertli.zero.dao.UserRegisterDao;
import robertli.zero.entity.User;
import robertli.zero.entity.UserRegister;
import robertli.zero.service.UserEmailBuilder;
import robertli.zero.service.UserRegisterService;
import robertli.zero.tool.ValidationTool;

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
@Component("userRegisterService")
public class UserRegisterServiceImpl implements UserRegisterService {

    /**
     * a register recode will be deleted after x minutes
     */
    public static final int REGISTER_LIFE_MINUTE = 60;
    public static final int VERIFIED_CODE_LENGTH = 30;

    @Resource
    private UserDao userDao;

    @Resource
    private UserRegisterDao userRegisterDao;

    @Resource
    private UserAuthDao userAuthDao;

    @Resource
    private RandomCodeCreater randomCodeCreater;

    @Resource
    private SecurityService securityService;

    @Resource
    private EmailSender emailSender;

    @Resource
    private UserEmailBuilder userEmailBuilder;

    private UserRegisterResult validateRegister(String userAuthId) {
        if (userAuthDao.isExist(userAuthId)) {
            return UserRegisterResult.USER_EXIST;
        }
        if (userRegisterDao.isExist(userAuthId)) {
            return UserRegisterResult.REGISTER_EXIST;
        }
        return null;
    }

    private UserRegisterResult register(String userAuthId, String authLabel, String authType, String orginealPassword, String name) {
        UserRegisterResult validateResult = validateRegister(userAuthId);
        if (validateResult != null) {
            return validateResult;
        }
        final String verifiedCode = randomCodeCreater.createRandomCode(VERIFIED_CODE_LENGTH, RandomCodeCreater.CodeType.MIX);
        final String passwordSalt = securityService.createPasswordSalt();
        final String password = securityService.uglifyPassoword(orginealPassword, passwordSalt);

        UserRegister register = new UserRegister();
        register.setAuthId(userAuthId);
        register.setAuthLabel(authLabel);
        register.setAuthType(authType);
        register.setName(name);
        register.setPassword(password);
        register.setPasswordSalt(passwordSalt);
        register.setSignDate(new Date());
        register.setVerifiedCode(verifiedCode);
        userRegisterDao.save(register);
        return UserRegisterResult.SUBMIT_SUCCESS;
    }

    @Override
    public boolean sendRegisterVerificationEmail(String email) {
        String authId = ValidationTool.preprocessEmail(email);
        UserRegister register = userRegisterDao.get(authId);
        if (register == null) {
            return true;
        }
        String verifiedCode = register.getVerifiedCode();
        EmailMessage emailMessage = userEmailBuilder.buildUserRegisterVerificationEmail(register.getAuthId(), register.getName(), verifiedCode);
        emailSender.send(emailMessage);
        return false;
    }

    @Override
    public boolean sendRegisterVerificationSMS(String telephone) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private UserRegisterResult validateRegisterByEmail(String email, String orginealPassword, String passwordAgain, String name) {
        if (ValidationTool.checkEmail(email.trim()) == false) {
            return UserRegisterResult.EMAIL_FORMAT_ERROR;
        }
        if (orginealPassword.length() < 8) {
            return UserRegisterResult.PASSWORD_SHORTER_THAN_8;
        }
        if (orginealPassword.equals(passwordAgain) == false) {
            return UserRegisterResult.PASSWORD_AGAIN_ERROR;
        }
        if (name == null || name.isEmpty()) {
            return UserRegisterResult.NO_NAME_ERROR;
        }
        return null;
    }

    @Override
    public UserRegisterResult registerByEmail(String email, String originalPassword, String passwordAgain, String name) {
        UserRegisterResult result = validateRegisterByEmail(email, originalPassword, passwordAgain, name);
        if (result != null) {
            return result;
        }
        String authLabel = email;
        String authId = ValidationTool.preprocessEmail(email);
        name = name.trim();
        final String authType = "email";
        email = ValidationTool.preprocessEmail(email);
        result = register(authId, authLabel, authType, originalPassword, name);
        if (result != UserRegisterResult.SUBMIT_SUCCESS) {
            return result;
        }
        boolean emailFail = sendRegisterVerificationEmail(email);
        if (emailFail) {
            return UserRegisterResult.EMAIL_SEND_FAIL;
        }
        return UserRegisterResult.SUBMIT_SUCCESS;
    }

    @Override
    public UserRegisterResult registerByTelephone(String telephone, String orginealPassword, String passwordAgain, String nickname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void createNewUser(UserRegister register) {
        String name = register.getName();
        String password = register.getPassword();
        String passwordSalt = register.getPasswordSalt();
        User user = userDao.saveUser(name, password, passwordSalt);

        String authId = register.getAuthId();
        String label = register.getAuthLabel();
        String type = register.getAuthType();
        userAuthDao.saveUserAuth(authId, label, type, user);
    }

    @Override
    public UserRegisterVerifiyResult verifiyRegister(String verifiedCode) {
        UserRegister register = userRegisterDao.getByVerifiedCode(verifiedCode);
        if (register == null) {
            return UserRegisterVerifiyResult.VERIFIY_FAIL;
        }

        if (userAuthDao.isExist(register.getAuthId())) {
            return UserRegisterVerifiyResult.VERIFIED_CODE;
        }
        createNewUser(register);
        return UserRegisterVerifiyResult.VERIFIY_SUCCESS;
    }

}

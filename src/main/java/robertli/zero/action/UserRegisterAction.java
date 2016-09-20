/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.action;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
import robertli.zero.service.UserRegisterService;
import robertli.zero.service.UserRegisterService.UserRegisterResult;
import robertli.zero.service.UserService;

/**
 *
 * @author Robert Li
 */
public class UserRegisterAction extends ActionSupport {

    private String email;
    private String name;
    private String password;
    private String passwordAgain;
    private String telephone;
    private String verifiedCode;

    @Resource
    private UserRegisterService userRegisterService;

    public String verify() {
        userRegisterService.verifiyRegister(verifiedCode);
        return SUCCESS;
    }

    public String sendVerifyEmail() {
        userRegisterService.sendRegisterVerificationEmail(email);
        return SUCCESS;
    }

    public void validateExecute() {
        if (email == null || email.length() == 0) {
            addFieldError("email", "email can't be null");
        }

        if (name == null || name.length() == 0) {
            addFieldError("name", "name can't be null");
        }

        if (password == null || password.length() == 0) {
            addFieldError("password", "password can't be null");
        } else if (password.length() < 8) {
            addFieldError("password", "password should be at least 8 letters");
        } else if (password.equals(passwordAgain) == false) {
            addFieldError("passwordAgain", "passwordAgain wrong");
        }
    }

    @Override
    public String execute() {
        UserRegisterResult result = userRegisterService.registerByEmail(email, password, passwordAgain, name);
        switch (result) {
            case SUBMIT_SUCCESS:
                return SUCCESS;
            case USER_EXIST:
                addFieldError("email", "this user is exist");
                return INPUT;
            case REGISTER_EXIST:
                addFieldError("email", "you have submited your register, please check your email to valid it");
                return INPUT;
            case EMAIL_SEND_FAIL:
                addActionError("validation email sending fail");
                return INPUT;
            case PASSWORD_AGAIN_ERROR:
                addFieldError("passwordAgain", "passwordAgain wrong");
                return INPUT;
            case PASSWORD_SHORTER_THAN_8:
                addFieldError("password", "password should be at least 8 letters");
            default:
                addActionError("some reason cause register fail");
                return INPUT;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }

    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getVerifiedCode() {
        return verifiedCode;
    }

    public void setVerifiedCode(String verifiedCode) {
        this.verifiedCode = verifiedCode;
    }

}

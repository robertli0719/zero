/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.action;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
import robertli.zero.service.UserPasswordResetService;
import robertli.zero.service.UserPasswordResetService.ResetPasswordResult;

/**
 *
 * @author Robert Li
 */
public class UserPasswordResetAction extends ActionSupport {

    @Resource
    private UserPasswordResetService userPasswordResetService;

    private String token;
    private String password;
    private String passwordAgain;

    @Override
    public String input() {
        if (token == null) {
            throw new RuntimeException("can't get Token");
        }
        return INPUT;
    }

    @Override
    public String execute() {
        ResetPasswordResult serviceResult = userPasswordResetService.resetPassword(token, password, passwordAgain);
        switch (serviceResult) {
            case SUCCESS:
                break;
            case PASSWORD_AGAIN_ERROR:
                addFieldError("passwordAgain", "two passwords are not equal");
                return INPUT;
            case PASSWORD_SHORTER_THAN_8:
                addFieldError("password", "password should be at least 8 letters");
                return INPUT;
            case CODE_WORING:
                addActionError("this link has been past due");
                return INPUT;
            case DATABASE_EXCEPTION:
                addActionError("database access fail, please try again later");
                return INPUT;
        }
        return SUCCESS;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

}

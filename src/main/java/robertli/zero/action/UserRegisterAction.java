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
package robertli.zero.action;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
import robertli.zero.service.UserRegisterService;
import robertli.zero.service.UserRegisterService.UserRegisterResult;

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

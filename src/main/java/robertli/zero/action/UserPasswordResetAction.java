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

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
import robertli.zero.service.UserPasswordResetService.CreatePasswordResetTokenResult;

/**
 *
 * @author Robert Li
 */
public class UserForgetPasswordAction extends ActionSupport {

    @Resource
    private UserPasswordResetService userPasswordResetService;

    private String email;
    private String result;

    @Override
    public String execute() {
        return SUCCESS;
    }

    public String askToken() {
        CreatePasswordResetTokenResult serviceResult = userPasswordResetService.createPasswordResetToken(email);

        switch (serviceResult) {
            case SUCCESS:
                result = "Password reseting email has sent successful, please check your email";
                break;
            case EMAIL_FORMAT_ERROR:
                result = "Email format is not correct";
                break;
            case USER_NOT_FOUND:
                result = "We're sorry. We weren't able to identify you given the information provided.";
                break;
            case DATABASE_EXCEPTION:
                result = "Database access wrong, please try again later.";
                break;
            default:
                result = "Error: can't identity this process result.";
        }
        System.out.println(result);
        return execute();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}

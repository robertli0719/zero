/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
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

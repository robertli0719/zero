/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.action.json;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
import robertli.zero.service.UserService;
import robertli.zero.struts2.SessionIdAware;

/**
 *
 * @author Robert Li
 */
public class UserAction extends ActionSupport implements SessionIdAware {

    @Resource
    private UserService userService;

    private String authId;
    private String password;
    private String sessionId;
    private String token;
    private String result = "fail";
    private String errorString = "none";

    public String login() {
        UserService.UserLoginResult serviceResult = userService.login(sessionId, authId, password);
        switch (serviceResult) {
            case SUCCESS:
                result = "success";
                break;
            case PASSWORD_WRONG:
                errorString = "password wrong";
                break;
            case USER_LOGINED:
                errorString = "There is anothr user login";
                break;
            case DATABASE_EXCEPTION:
                errorString = "database doen't work, please try again later.";
                break;
            default:
                errorString = "Error: can't identity login result";
                break;
        }
        return SUCCESS;
    }

    public String logout() {
        boolean fail = userService.logout(sessionId);
        result = fail ? "fail" : "success";
        errorString = fail ? "logout fail" : "";
        return SUCCESS;
    }

    public String loginByGoogle() {
        UserService.UserLoginByGoogleResult serviceResult = userService.loginByGoogle(sessionId, token);
        switch (serviceResult) {
            case SUCCESS:
                result = "success";
                break;
            case USER_LOGINED:
                errorString = "There is anothr user login";
                break;
            case NO_VERIFIED_EMAIL:
                errorString = "Can't found your valid email in your google account";
                break;
            case FAIL:
                errorString = "Login Fail";
                break;
        }
        return SUCCESS;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getResult() {
        return result;
    }

    public String getErrorString() {
        return errorString;
    }

}

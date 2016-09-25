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

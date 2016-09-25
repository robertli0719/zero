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
import robertli.zero.core.WebConfiguration;
import robertli.zero.entity.User;
import robertli.zero.service.UserService;
import robertli.zero.service.UserService.UserLoginResult;
import robertli.zero.struts2.SessionIdAware;
import robertli.zero.struts2.UserAware;

/**
 *
 * @author Robert Li
 */
public class UserLoginAction extends ActionSupport implements SessionIdAware, UserAware {

    @Resource
    private UserService userService;

    @Resource
    private WebConfiguration webConfiguration;

    private String authId;
    private String password;
    private String sessionId;
    private User user;
    private String googleSigninClientId;

    @Override
    public String input() {
        googleSigninClientId = webConfiguration.getGoogleSigninClientId();
        return INPUT;
    }

    @Override
    public String execute() {
        googleSigninClientId = webConfiguration.getGoogleSigninClientId();
        UserLoginResult result = userService.login(sessionId, authId, password);
        switch (result) {
            case SUCCESS:
                return SUCCESS;
            case PASSWORD_WRONG:
                addActionError("password wrong");
                return INPUT;
            case USER_LOGINED:
                addActionError("There is anothr user login");
                return INPUT;
            case DATABASE_EXCEPTION:
                addActionError("database doen't work, please try again later.");
                return INPUT;
            default:
                addActionError("Error: can't identity login result");
                return INPUT;
        }
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGoogleSigninClientId() {
        return googleSigninClientId;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}

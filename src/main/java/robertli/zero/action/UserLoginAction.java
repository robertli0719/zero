/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
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

    private String authId;
    private String password;
    private String sessionId;
    private User user;

    @Override
    public String input() {
        return INPUT;
    }

    @Override
    public String execute() {
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

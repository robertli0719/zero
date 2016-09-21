/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.action;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
import robertli.zero.service.UserService;
import robertli.zero.service.UserService.UserLoginResult;
import robertli.zero.struts2.SessionIdAware;

/**
 *
 * @author Robert Li
 */
public class UserLoginAction extends ActionSupport implements SessionIdAware {

    @Resource
    private UserService userService;

    private String authId;
    private String password;
    private String sessionId;

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

}

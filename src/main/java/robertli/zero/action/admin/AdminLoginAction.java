/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
import robertli.zero.entity.Admin;
import robertli.zero.service.AdminService;
import robertli.zero.struts2.SessionIdAware;

/**
 *
 * @author Robert Li
 */
public class AdminLoginAction extends ActionSupport implements SessionIdAware {

    @Resource
    private AdminService adminService;

    private String sessionId;
    private String username;
    private String password;

    @Override
    public void validate() {
        if (username == null || username.length() <= 0) {
            this.addFieldError("username", "username can't be null");
        }
        if (password == null || password.length() <= 0) {
            this.addFieldError("password", "password can't be null");
        }
    }

    @Override
    public String execute() {
        adminService.logout(sessionId);
        AdminService.AdminLoginResult result = adminService.login(sessionId, username, password);
        switch (result) {
            case PASSWORD_WRONG:
                addFieldError("password", "password is not correct");
                return INPUT;
            case SUSPENDED_STATUS:
                addFieldError("username", "your account has been suspended");
                return INPUT;
            case ADMIN_LOGINED:
                addFieldError("username", "someone has loged in now");
                return INPUT;
            case DATABASE_EXCEPTION:
                addFieldError("username", "system is busy, please try again later");
                return INPUT;
        }
        return "index";
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

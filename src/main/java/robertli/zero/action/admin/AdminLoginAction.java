/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
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

    @Override
    public String execute() {
        boolean fail = adminService.logout(sessionId);
        if (fail) {
            return "index";
        }
        return "login";
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.action;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
import robertli.zero.service.UserService;
import robertli.zero.struts2.SessionIdAware;

/**
 *
 * @author Robert Li
 */
public class UserLogoutAction extends ActionSupport implements SessionIdAware {

    @Resource
    private UserService userService;

    private String sessionId;

    @Override
    public String execute() {
        boolean fail = userService.logout(sessionId);
        if (fail) {
            throw new RuntimeException("logout fail");
        }
        return "index";
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

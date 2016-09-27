/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import javax.annotation.Resource;
import robertli.zero.entity.Admin;
import robertli.zero.service.AdminManagementService;
import robertli.zero.service.AdminService;
import robertli.zero.struts2.AdminRootPermission;
import robertli.zero.struts2.SessionIdAware;

/**
 *
 * @author Robert Li
 */
public class AdminAction extends ActionSupport implements AdminRootPermission, SessionIdAware {

    @Resource
    private AdminManagementService adminManagementService;

    @Resource
    private AdminService adminService;

    private List<Admin> adminList;
    private String sessionId;
    private String username;
    private String password;
    private boolean suspended;

    @Override
    public String execute() {
        adminList = adminManagementService.getAdminList(sessionId);
        return SUCCESS;
    }

    public String add() {
        boolean fail = adminManagementService.addAdmin(sessionId, username, password);
        if (fail) {
            this.addActionError(username + " add failed");
        } else {
            this.addActionMessage(username + " add successful");
        }
        return execute();
    }

    public String delete() {
        boolean fail = adminManagementService.deleteAdmin(sessionId, username);
        if (fail) {
            this.addActionError(username + " delete failed");
        } else {
            this.addActionMessage(username + " delete successful");
        }
        return execute();
    }

    public String resetPassword() {
        boolean fail = adminManagementService.resetPassword(sessionId, username, password);
        if (fail) {
            this.addActionError(username + "'s password reset failed");
        } else {
            this.addActionMessage(username + "'s password reset successful");
        }
        return execute();
    }

    public String resetSuspended() {
        boolean fail = adminManagementService.setSuspendStatus(sessionId, username, suspended);
        if (fail) {
            this.addActionError(username + "is suspened failed");
        } else {
            this.addActionMessage(username + "is suspened successful");
        }
        return execute();
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<Admin> getAdminList() {
        return adminList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

}

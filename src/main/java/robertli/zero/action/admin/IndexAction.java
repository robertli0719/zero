/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
import robertli.zero.service.AdminService;
import robertli.zero.service.AdminService.AdminResetPasswordResult;
import robertli.zero.struts2.SessionIdAware;

/**
 *
 * @author Robert Li
 */
public class IndexAction extends ActionSupport implements SessionIdAware {

    @Resource
    private AdminService adminService;

    private String oldPassword;
    private String newPassword;
    private String newPasswordAgain;
    private String sessionId;

    @Override
    public String execute() {
        return SUCCESS;
    }

    public String resetPassword() {
        AdminResetPasswordResult result = adminService.resetPassword(sessionId, oldPassword, newPassword, newPasswordAgain);
        switch (result) {
            case NEED_LOGIN:
                return LOGIN;
            case OLD_PASSWORD_WRONG:
                this.addFieldError("oldPassword", "wrong password");
                break;
            case NEW_PASSWORD_IS_TOO_EASY:
                this.addFieldError("newPassword", "password is too easy");
                break;
            case PASSWORD_AGAIN_ERROR:
                this.addFieldError("newPasswordAgain", "different passowrd");
                break;
            case FAIL:
                this.addFieldError("newPassword", "change is fail");
                break;
            case SUCCESS:
                this.addActionMessage("password is changed");
        }
        return execute();
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setNewPasswordAgain(String newPasswordAgain) {
        this.newPasswordAgain = newPasswordAgain;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}

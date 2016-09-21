/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.action;

import com.opensymphony.xwork2.ActionSupport;
import robertli.zero.entity.User;
import robertli.zero.struts2.SessionIdAware;
import robertli.zero.struts2.UserAware;

/**
 *
 * @author Robert Li
 */
public class IndexAction extends ActionSupport implements UserAware {

    private User user;

    @Override
    public String execute() {
        System.out.println("IndexAction execute..");
        return SUCCESS;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}

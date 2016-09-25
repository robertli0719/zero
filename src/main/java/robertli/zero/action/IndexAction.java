/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action;

import com.opensymphony.xwork2.ActionSupport;
import robertli.zero.entity.User;
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

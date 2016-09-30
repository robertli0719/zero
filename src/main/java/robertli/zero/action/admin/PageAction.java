/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import robertli.zero.struts2.SessionIdAware;

/**
 *
 * @version 1.0 2016-09-29
 * @author Robert Li
 */
public class PageAction extends ActionSupport implements SessionIdAware {

    private String sessionId;

    @Override
    public String execute() {
        return SUCCESS;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

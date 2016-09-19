/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.action;

import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author Robert Li
 */
public class IndexAction extends ActionSupport {

    @Override
    public String execute() {
        HttpServletRequest request = ServletActionContext.getRequest();
        String sessionId = request.getSession().getId();        
        System.out.println("sessionId:" + sessionId);
        System.out.println("IndexAction execute..");
        return SUCCESS;
    }
}

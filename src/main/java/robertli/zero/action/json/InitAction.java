/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.action.json;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
import robertli.zero.service.AdminService;

/**
 *
 * @author Robert Li
 */
public class InitAction extends ActionSupport {

    private String result;

    @Resource
    private AdminService adminService;

    @Override
    public String execute() {
        adminService.init();
        result = "SUCCESS";
        return SUCCESS;
    }

    public String getResult() {
        return result;
    }

}

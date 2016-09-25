/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.action;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
import robertli.zero.service.UserRegisterService;

/**
 *
 * @author Robert Li
 */
public class UserRegisterVerifyAction extends ActionSupport {

    @Resource
    private UserRegisterService userRegisterService;
    private String code;
    private String result;

    @Override
    public String execute() {
        UserRegisterService.UserRegisterVerifiyResult verifiyResult = userRegisterService.verifiyRegister(code);
        switch (verifiyResult) {
            case DATABASE_EXCEPTION:
                result = "datababse doesn't work, please try again later";
                return SUCCESS;
            case VERIFIY_SUCCESS:
                result = "verifiy success! you can start to use";
                return SUCCESS;
            case VERIFIED_CODE:
                result = "you have verified before";
                return SUCCESS;
            case VERIFIY_FAIL:
                result = "Error: can't verifiy this code";
                return SUCCESS;
            default:
                result = "Error: can't identity this verifiy result";
                return SUCCESS;
        }
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

}

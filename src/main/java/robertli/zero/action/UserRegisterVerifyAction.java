/*
 * The MIT License
 *
 * Copyright 2016 Robert Li.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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

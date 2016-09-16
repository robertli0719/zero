/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.action.json;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author Robert Li
 */
public class HelloAction extends ActionSupport {

    private String name;

    @Override
    public String execute() {
        name = "testName";
        return SUCCESS;
    }

    public String getName() {
        return name;
    }

}

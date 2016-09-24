/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.action;

import com.opensymphony.xwork2.ActionSupport;
import java.io.File;

/**
 *
 * @author Robert Li
 */
public class FileUploadDemoAction extends ActionSupport {

    private File fe[];
    private String feContentType[];
    private String feFileName[];

    @Override
    public String execute() throws InterruptedException {

        if (fe != null) {
            System.out.println(fe[0].getAbsoluteFile());
            System.out.println(feContentType[0]);
            System.out.println(feFileName[0]);
        }
        return SUCCESS;
    }

    public void setFe(File[] fe) {
        this.fe = fe;
    }

    public void setFeContentType(String[] feContentType) {
        this.feContentType = feContentType;
    }

    public void setFeFileName(String[] feFileName) {
        this.feFileName = feFileName;
    }

}

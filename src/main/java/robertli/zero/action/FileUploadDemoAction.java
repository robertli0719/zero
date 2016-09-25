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

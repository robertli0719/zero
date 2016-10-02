/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.struts2;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;
import java.io.PrintWriter;
import java.util.Map;
import org.apache.struts2.ServletActionContext;

/**
 * This is a result for struts2 <br>
 * We can use it for text response,such as JSON.For using this result, we need
 * to add it into struts XML configuration file first.Than implement
 * TextResultSupport on our Action
 *
 * @see TextResultSupport
 * @version 1.0 2016-09-29
 * @author Robert Li
 */
public class TextResult implements Result {
    
    @Override
    public void execute(ActionInvocation ai) throws Exception {
        ServletActionContext.getResponse().setContentType("text/plain");
        ServletActionContext.getResponse().setCharacterEncoding("utf-8");
        PrintWriter responseStream = ServletActionContext.getResponse().getWriter();
        ValueStack valueStack = ai.getStack();
        String text = (String) valueStack.findValue("textResult");
        responseStream.print(text);
        responseStream.flush();
    }
    
}

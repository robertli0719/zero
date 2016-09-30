/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.struts2;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;

/**
 * This is a result for struts2 <br>
 * We can use it for binary file response,such as download file and images.For
 * using this result, we need to add it into struts XML configuration file
 * first.Than implement FileResultSupport on our Action
 *
 * @see FileResultSupport
 * @version 1.0 2016-09-29
 * @author Robert Li
 */
public class FileResult implements Result {

    private static final long ONE_MONTH = 31 * 24 * 3600 * 1000;

    @Override
    public void execute(ActionInvocation ai) throws Exception {
        FileResultSupport support = (FileResultSupport) ai.getAction();
        HttpServletResponse response = ServletActionContext.getResponse();
        HttpServletRequest request = ServletActionContext.getRequest();

        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        long now = (new Date()).getTime();
        long expire = now + ONE_MONTH;
        long lastModifiedMillis = now;

        if (ifModifiedSince > 0 && ifModifiedSince <= lastModifiedMillis) {
            response.setStatus(304);
            response.flushBuffer();
            return;
        }
        response.setDateHeader("Date", now);
        response.setDateHeader("Expires", expire);
        response.setDateHeader("Retry-After", expire);
        response.setHeader("Cache-Control", "public");
        response.setDateHeader("Last-Modified", lastModifiedMillis);
        response.setContentType(support.getContentType());
        response.getOutputStream().write(support.getFileInBytes());
        response.getOutputStream().flush();
    }

}

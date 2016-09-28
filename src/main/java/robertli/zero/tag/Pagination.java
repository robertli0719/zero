/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.tag;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionProxy;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.struts2.ServletActionContext;
import robertli.zero.model.SearchResult;

/**
 * This is a JSP tag for paging.
 *
 * @version 1.0 2016-09-28
 * @author Robert Li
 */
public class Pagination extends SimpleTagSupport {

    private String result;

    private CommonTag createPreviousBtn(boolean disable) {
        CommonTag aTag = new CommonTag("a");
        aTag.addAttr("aria-label", "Previous");
        CommonTag liTag = new CommonTag("li");
        return liTag;
    }

    private CommonTag createUl() {
        CommonTag ulTag = new CommonTag("ul");
        return ulTag;
    }

    public String getPath() {
        ActionProxy proxy = ActionContext.getContext().getActionInvocation().getProxy();
        String namespace = proxy.getNamespace();
        String name = proxy.getActionName();
        return namespace + (name == null || name.equals("/") ? "" : ("/" + name));
    }

    public String createNewPath(int page) {
        String path = getPath();
        Map<String, Object> params = ActionContext.getContext().getParameters();
        if (params.isEmpty()) {
            return path;
        }
        String queryString = ServletActionContext.getRequest().getQueryString();
        System.out.println(queryString);
//        path += "?";
//        for (String key : params.keySet()) {
//            Object val = params.get(key);
//            path
//
//        }
        return null;

    }

    @Override
    public void doTag() throws IOException {
        System.out.println("hello");
        CommonTag navTag = new CommonTag("nav");
        navTag.addAttr("aria-label", "Page navigation");
        navTag.addChild(createUl());
        Writer out = getJspContext().getOut();

        System.out.println(getPath());
        String queryString = ServletActionContext.getRequest().getQueryString();
        System.out.println(queryString);

        Object obj = getJspContext().findAttribute(result);
        if (obj instanceof SearchResult == false) {
            return;
        }
        SearchResult searchResult = (SearchResult) obj;
        searchResult.getCount();
        System.out.println("sss");
        navTag.write(out);
    }

    public void setResult(String result) {
        this.result = result;
    }

}

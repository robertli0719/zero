/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
import robertli.zero.service.PageService;
import robertli.zero.struts2.SessionIdAware;
import robertli.zero.struts2.TextResultSupport;

/**
 * Unfinished
 *
 * @version Unfinished 2016-10-03
 * @author Robert Li
 */
public class PageAddAction extends ActionSupport implements SessionIdAware, TextResultSupport {

    @Resource
    private PageService pageService;

    private String title;
    private String category;
    private String description;
    private String content;
    private String sessionId;
    private String textResult;

    @Override
    public String execute() {

        return SUCCESS;
    }

    public String submit() {
        boolean fail = pageService.addPage(category, title, description, content);
        return TEXT;
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getTextResult() {
        return textResult;
    }
}

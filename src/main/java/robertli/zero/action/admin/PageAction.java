/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import javax.annotation.Resource;
import robertli.zero.entity.Page;
import robertli.zero.service.PageService;
import robertli.zero.struts2.SessionIdAware;

/**
 *
 * @version 1.0 2016-09-29
 * @author Robert Li
 */
public class PageAction extends ActionSupport implements SessionIdAware {

    @Resource
    private PageService pageService;

    private String sessionId;
    private String category;
    private int id;
    private boolean opened;
    private List<Page> pageList;

    @Override
    public String execute() {
        pageList = pageService.listAll();
        return SUCCESS;
    }

    public String listByCategory() {
        pageList = pageService.listAll(category);
        return SUCCESS;
    }

    public String delete() {
        pageService.removePage(id);
        return execute();
    }

    public String resetStatus() {
        pageService.setStatus(id, opened);
        return execute();
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public List<Page> getPageList() {
        return pageList;
    }

    public void setPageList(List<Page> pageList) {
        this.pageList = pageList;
    }

}

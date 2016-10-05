/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
import robertli.zero.entity.Page;
import robertli.zero.service.PageService;

/**
 *
 * @author Robert Li
 */
public class PageAction extends ActionSupport {

    @Resource
    private PageService pageService;

    private int id;
    private Page page;

    @Override
    public String execute() {
        page = pageService.getPage(id);
        if (page != null && page.isOpened() == false) {
            page = null;
        }
        return SUCCESS;
    }

    public String delete() {
        pageService.removePage(id);
        return execute();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}

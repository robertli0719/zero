/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import javax.annotation.Resource;
import robertli.zero.entity.PageCategory;
import robertli.zero.service.PageService;
import robertli.zero.struts2.SessionIdAware;

/**
 *
 * @version 1.0 2016-10-03
 * @author Robert Li
 */
public class PageCategoryAction extends ActionSupport implements SessionIdAware {

    @Resource
    private PageService pageService;

    private String sessionId;
    private String name;
    private String description;

    private List<PageCategory> pageCategoryList;

    @Override
    public String execute() {
        pageCategoryList = pageService.listCategory();
        return SUCCESS;
    }

    public String add() {
        pageService.addCategory(name, description);
        return execute();
    }

    public String delete() {
        pageService.removeCategory(name);
        return execute();
    }

    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<PageCategory> getPageCategoryList() {
        return pageCategoryList;
    }

    public void setPageCategoryList(List<PageCategory> pageCategoryList) {
        this.pageCategoryList = pageCategoryList;
    }

}

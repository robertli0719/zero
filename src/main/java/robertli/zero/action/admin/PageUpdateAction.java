/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
import robertli.zero.core.JsonService;
import robertli.zero.entity.Page;
import robertli.zero.service.PageService;
import robertli.zero.service.PageService.UpdatePageResult;
import robertli.zero.struts2.TextResultSupport;

/**
 *
 * @version 1.0 2016-10-04
 * @author Robert Li
 */
public class PageUpdateAction extends ActionSupport implements TextResultSupport {

    @Resource
    private PageService pageService;

    @Resource
    private JsonService jsonService;

    private int id;
    private String title;
    private String category;
    private String content;
    private String description;
    private String textResult;

    @Override
    public String execute() {
        Page page = pageService.getPage(id);
        title = page.getTitle();
        category = page.getCategory().getName();
        content = page.getContent();
        description = page.getDescription();
        return SUCCESS;
    }

    private String makeFailResult(String errorString) {
        return jsonService.createFailResult(errorString).toString();
    }

    public String update() {
        UpdatePageResult result = pageService.updatePage(id, title, description, content);
        switch (result) {
            case SUCCESS:
                textResult = jsonService.createSuccessResult().toString();
                break;
            case FAIL:
                textResult = makeFailResult("update page fail");
                break;
            case NO_TITLE:
                textResult = makeFailResult("title can't be null");
                break;
            case NO_CONTENT:
                textResult = makeFailResult("content can't be null");
                break;
            default:
                textResult = makeFailResult("can't identity the result");

        }
        return TEXT;
    }

    @Override
    public String getTextResult() {
        return textResult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

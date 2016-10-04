/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import javax.annotation.Resource;
import robertli.zero.core.JsonService;
import robertli.zero.service.PageService;
import robertli.zero.service.PageService.AddPageResult;
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

    @Resource
    private JsonService jsonService;

    private String title;
    private String category;
    private String description;
    private String content;
    private String sessionId;
    private String textResult;

    private List<String> pageCategoryNameList;

    @Override
    public String execute() {
        pageCategoryNameList = pageService.listCategoryName();
        return SUCCESS;
    }

    private String makeFailResult(String errorString) {
        return jsonService.createFailResult(errorString).toString();
    }

    public String submit() {
        AddPageResult result = pageService.addPage(category, title, description, content);
        switch (result) {
            case SUCCESS:
                textResult = jsonService.createSuccessResult().toString();
                break;
            case FAIL:
                textResult = makeFailResult("add page fail");
                break;
            case NO_CATEGORY:
                textResult = makeFailResult("category can't be null");
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
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getTextResult() {
        return textResult;
    }

    public List<String> getPageCategoryNameList() {
        return pageCategoryNameList;
    }

    public void setPageCategoryNameList(List<String> pageCategoryNameList) {
        this.pageCategoryNameList = pageCategoryNameList;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

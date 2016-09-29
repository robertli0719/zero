/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.admin;

import com.opensymphony.xwork2.ActionSupport;
import javax.annotation.Resource;
import robertli.zero.entity.User;
import robertli.zero.model.SearchResult;
import robertli.zero.service.UserManagementService;

/**
 *
 * @author Robert Li
 */
public class UserAction extends ActionSupport {

    private final int PAGE_SIZE = 20;
    private int page = 1;
    private String keyword = "";
    private String field = "name";
    private SearchResult<User> userSearchResult;

    @Resource
    private UserManagementService userManagementService;

    @Override
    public String execute() {
        userSearchResult = userManagementService.getUserList(page, PAGE_SIZE);
        return SUCCESS;
    }

    public String search() {
        switch (field) {
            case "name":
                userSearchResult = userManagementService.searchUserByName(keyword, page, PAGE_SIZE);
                break;
            case "authorizationId":
                userSearchResult = userManagementService.searchUserByAuthId(keyword, page, PAGE_SIZE);
                break;
            default:
                return execute();
        }
        return SUCCESS;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public SearchResult<User> getUserSearchResult() {
        return userSearchResult;
    }

}

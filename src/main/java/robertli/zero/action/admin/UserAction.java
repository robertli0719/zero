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
    private SearchResult<User> userSearchResult;

    @Resource
    private UserManagementService userManagementService;

    @Override
    public String execute() {
        userSearchResult = userManagementService.getUserList(page, PAGE_SIZE);
        return SUCCESS;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public SearchResult<User> getUserSearchResult() {
        return userSearchResult;
    }

}

/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.dao.UserDao;
import robertli.zero.dao.UserOnlineDao;
import robertli.zero.entity.User;
import robertli.zero.entity.UserOnline;
import robertli.zero.model.SearchResult;
import robertli.zero.service.UserManagementService;

@Component("userManagementService")
public class UserManagementServiceImpl implements UserManagementService {

    @Resource
    private UserDao userDao;

    @Resource
    private UserOnlineDao userOnlineDao;

    @Override
    public SearchResult<User> getUserList(int pageId, int max) {
        return userDao.paging(pageId, max);
    }

    @Override
    public SearchResult<UserOnline> getUserOnlineList(int pageId, int max) {
        return userOnlineDao.paging(pageId, max);
    }

}

/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.dao.UserDao;
import robertli.zero.entity.User;
import robertli.zero.dto.SearchResult;
import robertli.zero.service.UserManagementService;

@Component("userManagementService")
public class UserManagementServiceImpl implements UserManagementService {

    @Resource
    private UserDao userDao;

    @Override
    public SearchResult<User> getUserList(int pageId, int max) {
        return userDao.paging(pageId, max);
    }

    @Override
    public SearchResult<User> searchUserByName(String name, int pageId, int max) {
        return userDao.searchByName(name, pageId, max);
    }

    @Override
    public SearchResult<User> searchUserByAuthId(String authId, int pageId, int max) {
        return userDao.searchByAuthId(authId, pageId, max);
    }

}

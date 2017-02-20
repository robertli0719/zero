/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import java.util.List;
import robertli.zero.entity.User;
import robertli.zero.dto.SearchResult;

/**
 *
 * @version 1.0.1 2017-01-27
 * @author Robert Li
 */
public interface UserDao extends GenericDao<User, Integer> {

    public User saveUser(String name, String password, String passwordSalt);

    public User getUserByUid(String uid);

    public SearchResult<User> paging(int pageId, int max);

    public SearchResult<User> searchByName(String name, int pageId, int max);

    public SearchResult<User> searchByAuthId(String authId, int pageId, int max);

    public List<User> getUserListByPlatform(String userPlatformName);

    public List<User> getUserListByRole(String userRoleName);
}

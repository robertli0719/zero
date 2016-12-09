/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import robertli.zero.entity.User;
import robertli.zero.dto.SearchResult;

/**
 * This service is design for administrators to manage user data.
 *
 * @version 1.0 2016-09-27
 * @author Robert Li
 */
public interface UserManagementService {

    /**
     * This function should fetch UserAuth together
     *
     * @param pageId the number of page
     * @param max the max lines to fetch
     * @return the list of users
     */
    public SearchResult<User> getUserList(int pageId, int max);

    /**
     * Search user by a keyword
     *
     * @param name the search name
     * @param pageId the number of page
     * @param max the max lines to fetch
     * @return the list of users
     */
    public SearchResult<User> searchUserByName(String name, int pageId, int max);

    /**
     * Search user by a keyword
     *
     * @param authId the authId in UserAuth
     * @param pageId the number of page
     * @param max the max lines to fetch
     * @return the list of users
     */
    public SearchResult<User> searchUserByAuthId(String authId, int pageId, int max);

    
}

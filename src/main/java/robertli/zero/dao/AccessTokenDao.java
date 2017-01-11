/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import robertli.zero.entity.AccessToken;

/**
 *
 * @author Robert Li
 */
public interface AccessTokenDao extends GenericDao<AccessToken, String> {

    /**
     * delete the lines which is out of date.
     */
    public void clear();

    public void deleteByUser(int userId);
}

/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import robertli.zero.entity.UserRoleItem;

/**
 *
 * @version 1.0.3 2016-01-05
 * @author Robert Li
 */
public interface UserRoleItemDao extends GenericDao<UserRoleItem, Integer> {

    public boolean isExist(int userId, String userRoleName);

    public void put(int userId, String userRoleName);

    public void remove(int userId, String userRoleName);

}

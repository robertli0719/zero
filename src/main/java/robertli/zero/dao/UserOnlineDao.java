/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import robertli.zero.entity.User;
import robertli.zero.entity.UserOnline;

/**
 *
 * @author Robert Li
 */
public interface UserOnlineDao extends GenericDao<UserOnline, String> {

    /**
     * delete the online user who has no activities within lifeMinute
     *
     * @param lifeMinute
     */
    public void clear(final int lifeMinute);

    public void saveUserOnline(String sessionId, User user);
}

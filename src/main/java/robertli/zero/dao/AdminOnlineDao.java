/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import org.springframework.stereotype.Component;
import robertli.zero.entity.Admin;
import robertli.zero.entity.AdminOnline;

/**
 *
 * @author Robert Li
 */
@Component("adminOnlineDao")
public interface AdminOnlineDao extends GenericDao<AdminOnline, String> {

    public void clear(final int lifeMinute);

    public void saveAdminOnline(String sessionId, Admin admin);

    /**
     * remove AdminOnline by username
     *
     * @param username
     */
    public void kickAdminByUsername(String username);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.dao;

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

}

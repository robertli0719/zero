/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.dao;

import robertli.zero.entity.UserPasswordResetToken;

/**
 *
 * @author Robert Li
 */
public interface UserPasswordResetTokenDao extends GenericDao<UserPasswordResetToken, String> {

    /**
     * delete the tokens which is past due
     *
     * @param lifeMinute
     */
    public void clear(final int lifeMinute);
}

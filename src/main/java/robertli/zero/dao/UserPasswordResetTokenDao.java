/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
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
     * @param lifeMinute  the time range for a token in minute
     */
    public void clear(final int lifeMinute);
}

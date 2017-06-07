/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import robertli.zero.entity.UserMobileBindingToken;

/**
 *
 * @author Robert Li
 */
public interface UserMobileBindingTokenDao extends GenericDao<UserMobileBindingToken, Integer> {

    /**
     * delete the tokens which is past due
     *
     * @param lifeMinute the time range for a token in minute
     */
    public void clear(final int lifeMinute);

    public UserMobileBindingToken get(String phoneNumber, String code);
}

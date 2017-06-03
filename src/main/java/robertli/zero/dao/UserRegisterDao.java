/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import robertli.zero.entity.UserRegister;

/**
 *
 * @author Robert Li
 */
public interface UserRegisterDao extends GenericDao<UserRegister, String> {

    /**
     * delete the register records which have no activities within lifeMinute
     *
     * @param lifeMinute the time range for a register in minute
     */
    public void clear(final int lifeMinute);

    /**
     *
     * @param code verifiedCode
     * @return true if verifiedCode is exist.
     */
    public boolean isExistVerifiedCode(String code);

    /**
     *
     * @param code verifiedCode
     * @return the UserRegister for verifiedCode
     */
    public UserRegister getByVerifiedCode(String code);
}

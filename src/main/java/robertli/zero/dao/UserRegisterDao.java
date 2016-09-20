/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
     * @param lifeMinute
     */
    public void clear(final int lifeMinute);

    /**
     *
     * @param code verifiedCode
     * @return the UserRegister for verifiedCode or null if not found
     */
    public UserRegister getByVerifiedCode(String code);
}

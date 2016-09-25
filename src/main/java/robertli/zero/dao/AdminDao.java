/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import robertli.zero.entity.Admin;

/**
 *
 * @author Robert Li
 */
public interface AdminDao extends GenericDao<Admin, String> {

    /**
     * This function is designed for inserting default administrator record when
     * execute the system first time. <br>
     *
     * This function will create the administrator only when this username is
     * not exist.
     *
     * @param username the username of the administrator
     * @param orginealPassword the real password for this administrator
     */
    public void initAdmin(String username, String orginealPassword);
}

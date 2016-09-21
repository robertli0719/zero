/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.dao;

import robertli.zero.entity.User;

/**
 *
 * @author Robert Li
 */
public interface UserDao extends GenericDao<User, Integer> {

    public User saveUser(String name, String password, String passwordSalt);
}
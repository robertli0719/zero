/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.UserRegisterDao;
import robertli.zero.entity.UserRegister;

/**
 *
 * @author Robert Li
 */
@Component("userRegisterDao")
public class UserRegisterDaoImpl extends GenericHibernateDao<UserRegister, Integer> implements UserRegisterDao {

}

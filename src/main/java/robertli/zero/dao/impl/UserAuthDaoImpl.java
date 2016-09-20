/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.UserAuthDao;
import robertli.zero.entity.UserAuth;

@Component("userAuthDao")
public class UserAuthDaoImpl extends GenericHibernateDao<UserAuth, String> implements UserAuthDao {

}

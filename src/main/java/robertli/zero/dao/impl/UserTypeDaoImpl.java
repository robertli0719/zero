/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.UserTypeDao;
import robertli.zero.entity.UserType;

/**
 *
 * @author Robert Li
 */
@Component("userTypeDao")
public class UserTypeDaoImpl extends GenericHibernateDao<UserType, String> implements UserTypeDao {
    
}

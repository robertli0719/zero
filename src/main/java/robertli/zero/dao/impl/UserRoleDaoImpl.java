/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.UserRoleDao;
import robertli.zero.entity.UserRole;

/**
 *
 * @author Robert Li
 */
@Component("userRoleDao")
public class UserRoleDaoImpl extends GenericHibernateDao<UserRole, String> implements UserRoleDao {

}

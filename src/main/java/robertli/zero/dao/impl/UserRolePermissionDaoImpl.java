/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.UserRolePermissionDao;
import robertli.zero.entity.UserRolePermission;

/**
 *
 * @author Robert Li
 */
@Component("userRolePermissionDao")
public class UserRolePermissionDaoImpl extends GenericHibernateDao<UserRolePermission, Integer> implements UserRolePermissionDao {

}

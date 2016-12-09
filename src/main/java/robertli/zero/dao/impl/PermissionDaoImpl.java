/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.PermissionDao;
import robertli.zero.entity.Permission;

/**
 *
 * @author Robert Li
 */
@Component("permissionDao")
public class PermissionDaoImpl extends GenericHibernateDao<Permission, String> implements PermissionDao {

}

/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.AdminPermissionDao;
import robertli.zero.entity.AdminPermission;

@Component("adminPermissionDao")
public class AdminPermissionDaoImpl extends GenericHibernateDao<AdminPermission, String> implements AdminPermissionDao {

}

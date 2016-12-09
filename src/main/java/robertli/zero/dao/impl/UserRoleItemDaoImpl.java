/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.UserRoleItemDao;
import robertli.zero.entity.UserRoleItem;

/**
 *
 * @author Robert Li
 */
@Component("userRoleItemDao")
public class UserRoleItemDaoImpl extends GenericHibernateDao<UserRoleItem, Integer> implements UserRoleItemDao {

}

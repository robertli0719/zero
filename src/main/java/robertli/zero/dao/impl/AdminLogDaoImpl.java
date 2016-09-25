/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.AdminLogDao;
import robertli.zero.entity.AdminLog;

/**
 *
 * @author Robert Li
 */
@Component("adminLogDao")
public class AdminLogDaoImpl extends GenericHibernateDao<AdminLog, Integer> implements AdminLogDao {

}

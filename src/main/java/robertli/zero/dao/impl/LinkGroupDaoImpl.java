/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.LinkGroupDao;
import robertli.zero.entity.LinkGroup;

/**
 *
 * @author Robert Li
 */
@Component("linkGroupDao")
public class LinkGroupDaoImpl extends GenericHibernateDao<LinkGroup, Integer> implements LinkGroupDao {

}

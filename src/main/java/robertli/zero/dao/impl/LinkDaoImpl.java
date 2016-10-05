/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.LinkDao;
import robertli.zero.entity.Link;

/**
 *
 * @author Robert Li
 */
@Component("linkDao")
public class LinkDaoImpl extends GenericHibernateDao<Link, Integer> implements LinkDao {

}

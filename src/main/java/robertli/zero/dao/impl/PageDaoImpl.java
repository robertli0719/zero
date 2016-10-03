/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.PageDao;
import robertli.zero.entity.Page;

/**
 *
 * @author Robert Li
 */
@Component("pageDao")
public class PageDaoImpl extends GenericHibernateDao<Page, Integer> implements PageDao {

}

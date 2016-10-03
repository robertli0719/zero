/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.PageCategoryDao;
import robertli.zero.entity.PageCategory;

/**
 *
 * @author Robert Li
 */
@Component("pageCategoryDao")
public class PageCategoryDaoImpl extends GenericHibernateDao<PageCategory, String> implements PageCategoryDao {

}

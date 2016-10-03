/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.PageImageDao;
import robertli.zero.entity.PageImage;

/**
 *
 * @author Robert Li
 */
@Component("pageImageDao")
public class PageImageDaoImpl extends GenericHibernateDao<PageImage, Integer> implements PageImageDao {

}

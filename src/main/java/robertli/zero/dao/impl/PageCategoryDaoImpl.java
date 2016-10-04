/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.dao.PageCategoryDao;
import robertli.zero.entity.PageCategory;

/**
 *
 * @author Robert Li
 */
@Component("pageCategoryDao")
public class PageCategoryDaoImpl extends GenericHibernateDao<PageCategory, String> implements PageCategoryDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public List<String> listName() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select name from PageCategory");
        return query.list();
    }

}

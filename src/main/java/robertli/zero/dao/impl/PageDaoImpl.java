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
import robertli.zero.dao.PageDao;
import robertli.zero.entity.Page;

/**
 *
 * @author Robert Li
 */
@Component("pageDao")
public class PageDaoImpl extends GenericHibernateDao<Page, Integer> implements PageDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public List<Page> listByCategory(String category) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Page where category = :category");
        query.setString("category", category);
        return query.list();
    }

}

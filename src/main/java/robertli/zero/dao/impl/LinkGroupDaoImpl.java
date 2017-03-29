/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.dao.LinkGroupDao;
import robertli.zero.entity.LinkGroup;

/**
 *
 * @author Robert Li
 */
@Component("linkGroupDao")
public class LinkGroupDaoImpl extends GenericHibernateDao<LinkGroup, Integer> implements LinkGroupDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public List<String> getNamespaceList() {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select lg.namespace from LinkGroup lg group by lg.namespace");
        return query.getResultList();
    }

    @Override
    public List<String> getPageNameList(String namespace) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select lg.pageName from LinkGroup lg where lg.namespace=:namespace group by lg.pageName");
        query.setParameter("namespace", namespace);
        return query.getResultList();
    }

    @Override
    public List<LinkGroup> getLinkGroupList(String namespace, String pageName) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select lg from LinkGroup lg where lg.namespace=:namespace and lg.pageName=:pageName");
        query.setParameter("namespace", namespace);
        query.setParameter("pageName", pageName);
        return query.getResultList();
    }

    @Override
    public List<String> getNameList(String namespace, String pageName) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select lg.name from LinkGroup lg where lg.namespace=:namespace and lg.pageName=:pageName");
        query.setParameter("namespace", namespace);
        query.setParameter("pageName", pageName);
        return query.getResultList();
    }

    @Override
    public LinkGroup getLinkGroup(String namespace, String pageName, String name) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select lg from LinkGroup lg where lg.namespace=:namespace and lg.pageName=:pageName and lg.name=:name");
        query.setParameter("namespace", namespace);
        query.setParameter("pageName", pageName);
        query.setParameter("name", name);
        query.setMaxResults(1);
        return (LinkGroup) query.getSingleResult();
    }

    @Override
    public boolean isExist(String namespace, String pageName, String name) {
        return getLinkGroup(namespace, pageName, name) != null;
    }

    @Override
    public void addLinkGroup(String namespace, String pageName, String name, String comment, int picWidth, int picHeight) {
        LinkGroup linkGroup = new LinkGroup();
        linkGroup.setNamespace(namespace);
        linkGroup.setPageName(pageName);
        linkGroup.setName(name);
        linkGroup.setComment(comment);
        save(linkGroup);
    }

    @Override
    public void deleteLinkGroup(String namespace, String pageName, String name) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select lg from LinkGroup lg where lg.namespace=:namespace and lg.pageName=:pageName and lg.name=:name");
        query.setParameter("namespace", namespace);
        query.setParameter("pageName", pageName);
        query.setParameter("name", name);
        query.setMaxResults(1);
        LinkGroup linkGroup = (LinkGroup) query.getSingleResult();
        delete(linkGroup);
    }

}

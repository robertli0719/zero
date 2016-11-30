/*
 * Copyright 2016 Robert Li.
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
        TypedQuery query = session.createQuery("select namespace from LinkGroup group by namespace");
        return query.getResultList();
    }

    @Override
    public List<String> getPageNameList(String namespace) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select pageName from LinkGroup where namespace=:namespace group by pageName");
        query.setParameter("namespace", namespace);
        return query.getResultList();
    }

    @Override
    public List<LinkGroup> getLinkGroupList(String namespace, String pageName) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("from LinkGroup where namespace=:namespace and pageName=:pageName");
        query.setParameter("namespace", namespace);
        query.setParameter("pageName", pageName);
        return query.getResultList();
    }

    @Override
    public List<String> getNameList(String namespace, String pageName) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select name from LinkGroup where namespace=:namespace and pageName=:pageName");
        query.setParameter("namespace", namespace);
        query.setParameter("pageName", pageName);
        return query.getResultList();
    }

    @Override
    public LinkGroup getLinkGroup(String namespace, String pageName, String name) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("from LinkGroup where namespace=:namespace and pageName=:pageName and name=:name");
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
        linkGroup.setPicWidth(picWidth);
        linkGroup.setPicHeight(picHeight);
        save(linkGroup);
    }

    @Override
    public void deleteLinkGroup(String namespace, String pageName, String name) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery delete_query = session.createQuery("delete from LinkGroup where namespace=:namespace and pageName=:pageName and name=:name");
        delete_query.setParameter("namespace", namespace);
        delete_query.setParameter("pageName", pageName);
        delete_query.setParameter("name", name);
        delete_query.executeUpdate();
    }

}

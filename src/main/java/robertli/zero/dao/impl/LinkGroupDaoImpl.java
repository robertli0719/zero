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
        Query query = session.createQuery("select namespace from LinkGroup group by namespace");
        return query.list();
    }

    @Override
    public List<String> getPageNameList(String namespace) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select pageName from LinkGroup where namespace=:namespace group by pageName");
        query.setString("namespace", namespace);
        return query.list();
    }

    @Override
    public List<LinkGroup> getLinkGroupList(String namespace, String pageName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from LinkGroup where namespace=:namespace and pageName=:pageName");
        query.setString("namespace", namespace);
        query.setString("pageName", pageName);
        return query.list();
    }

    @Override
    public List<String> getNameList(String namespace, String pageName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select name from LinkGroup where namespace=:namespace and pageName=:pageName");
        query.setString("namespace", namespace);
        query.setString("pageName", pageName);
        return query.list();
    }

    @Override
    public LinkGroup getLinkGroup(String namespace, String pageName, String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from LinkGroup where namespace=:namespace and pageName=:pageName and name=:name");
        query.setString("namespace", namespace);
        query.setString("pageName", pageName);
        query.setString("name", name);
        query.setMaxResults(1);
        return (LinkGroup) query.uniqueResult();
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
        Query delete_query = session.createQuery("delete from LinkGroup where namespace=:namespace and pageName=:pageName and name=:name");
        delete_query.setString("namespace", namespace);
        delete_query.setString("pageName", pageName);
        delete_query.setString("name", name);
        delete_query.executeUpdate();
    }

}

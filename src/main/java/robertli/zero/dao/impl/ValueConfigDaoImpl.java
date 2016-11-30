/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.dao.ValueConfigDao;
import robertli.zero.entity.ValueConfig;

/**
 *
 * @author Robert Li
 */
@Component("valueConfigDao")
public class ValueConfigDaoImpl extends GenericHibernateDao<ValueConfig, Integer> implements ValueConfigDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public List<String> getNamespaceList() {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select namespace from ValueConfig group by namespace");
        return query.getResultList();
    }

    @Override
    public List<String> getPageNameList(String namespace) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select pageName from ValueConfig where namespace=:namespace group by pageName");
        query.setParameter("namespace", namespace);
        return query.getResultList();
    }

    @Override
    public List<ValueConfig> getValueConfigList(String namespace, String pageName) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("from ValueConfig where namespace=:namespace and pageName=:pageName");
        query.setParameter("namespace", namespace);
        query.setParameter("pageName", pageName);
        return query.getResultList();
    }

    @Override
    public List<String> getValList(String namespace, String pageName, String name) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select val from ValueConfig where namespace=:namespace and pageName=:pageName and name=:name");
        query.setParameter("namespace", namespace);
        query.setParameter("pageName", pageName);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public void insertValueConfig(String namespace, String pageName, String name, String val) {
        Session session = sessionFactory.getCurrentSession();
        ValueConfig vc = new ValueConfig();
        vc.setPageName(pageName);
        vc.setNamespace(namespace);
        vc.setName(name);
        vc.setVal(val);
        session.save(vc);
    }

    @Override
    public void deleteValueConfig(String namespace, String pageName) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery delete_query = session.createQuery("delete from ValueConfig where namespace=:namespace and pageName=:pageName");
        delete_query.setParameter("namespace", namespace);
        delete_query.setParameter("pageName", pageName);
        delete_query.executeUpdate();
    }

    @Override
    public void updateValueConfig(String namespace, String pageName, Map<String, List<String>> valueConfigMap) {
        deleteValueConfig(namespace, pageName);

        for (String name : valueConfigMap.keySet()) {
            List<String> valList = valueConfigMap.get(name);
            for (String val : valList) {
                insertValueConfig(namespace, pageName, name, val);
            }
        }
    }

}

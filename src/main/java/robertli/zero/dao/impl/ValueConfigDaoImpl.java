/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.hibernate.Query;
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
    public List<String> getDomainList() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select domain from ValueConfig group by domain");
        return query.list();
    }

    @Override
    public List<String> getActionList(String domain) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select action from ValueConfig where domain=:domain group by action");
        return query.list();
    }

    @Override
    public List<ValueConfig> getValueConfigList(String domain, String action) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from ValueConfig where domain=:domain and action=:action");
        return query.list();
    }

    @Override
    public List<String> getValueList(String domain, String action, String keyname) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select value from ValueConfig where domain=:domain and action=:action and keyname=:keyname");
        query.setString("domain", domain);
        query.setString("action", action);
        query.setString("keyname", keyname);
        return query.list();
    }

    @Override
    public void insertValueConfig(String domain, String action, String keyname, String value) {
        Session session = sessionFactory.getCurrentSession();
        ValueConfig vc = new ValueConfig();
        vc.setAction(action);
        vc.setDomain(domain);
        vc.setKeyname(keyname);
        vc.setValue(value);
        session.save(vc);
    }

    @Override
    public void deleteValueConfig(String domain, String action) {
        Session session = sessionFactory.getCurrentSession();
        Query delete_query = session.createQuery("delete from ValueConfig where domain=:domain and action=:action");
        delete_query.setString("domain", domain);
        delete_query.setString("action", action);
        delete_query.executeUpdate();
    }

    @Override
    public void updateValueConfig(String domain, String action, Map<String, List<String>> valueConfigMap) {
        deleteValueConfig(domain, action);

        for (String keyname : valueConfigMap.keySet()) {
            List<String> valList = valueConfigMap.get(keyname);
            for (String val : valList) {
                insertValueConfig(domain, action, keyname, val);
            }
        }
    }

}

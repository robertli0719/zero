/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.TypedQuery;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import robertli.zero.dao.GenericDao;

/**
 *
 * @version 1.0.5 2017-04-10
 * @author Robert Li
 * @param <T> The Entity Class
 * @param <PK> The Type of ID
 */
public class GenericHibernateDao<T extends Serializable, PK extends Serializable> implements GenericDao<T, PK> {

    @Resource
    private SessionFactory sessionFactory;

    private Class<T> entityClass;

    public GenericHibernateDao() {
        this.entityClass = null;
        Type type = getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] p = ((ParameterizedType) type).getActualTypeArguments();
            this.entityClass = (Class<T>) p[0];
        }
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    private TypedQuery makeTypedQuery() {
        String jpql = "select t from " + entityClass.getName() + " t";
        return getSession().createQuery(jpql);
    }

    private TypedQuery makeTypedQueryDesc(String colName) {
        String jpql = "select t from " + entityClass.getName() + " t order by " + colName + " desc";
        return getSession().createQuery(jpql);
    }

    @Override
    public T get(PK id) {
        return (T) getSession().get(entityClass, id);
    }

    @Override
    public T getForUpdate(PK id) {
        return (T) getSession().get(entityClass, id, LockOptions.UPGRADE);
    }

    @Override
    public boolean isExist(PK id) {
        return get(id) != null;
    }

    @Override
    public void save(T entity) {
        getSession().save(entity);
    }

    @Override
    public void saveOrUpdate(T entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }

    @Override
    public void deleteById(PK id) {
        T entity = get(id);
        if (entity != null) {
            getSession().delete(entity);
        }
    }

    @Override
    public int count() {
        Number number = (Number) getSession().createQuery("select count(u) from " + entityClass.getName() + " u").getSingleResult();
        return number.intValue();
    }

    @Override
    public void flush() {
        getSession().flush();
    }

    @Override
    public List<T> list() {
        return makeTypedQuery().getResultList();
    }

    @Override
    public List<T> list(int limit) {
        return makeTypedQuery()
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<T> list(int offset, int limit) {
        return makeTypedQuery()
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<T> listDesc(String colName) {
        return makeTypedQueryDesc(colName).getResultList();
    }

    @Override
    public List<T> listDesc(String colName, int limit) {
        return makeTypedQueryDesc(colName)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<T> listDesc(String colName, int offset, int limit) {
        return makeTypedQueryDesc(colName)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

}

/*
 * Copyright 2016 Robert Li.
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
import robertli.zero.dto.SearchResult;

/**
 *
 * @version 1.0.3 2016-12-29
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
    public T getLast(String colName) {
        List<T> resultList = makeTypedQueryDesc(colName).setMaxResults(1).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
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
    public long count() {
        Number number = (Number) getSession().createQuery("select count(u) from " + entityClass.getName() + " u").getSingleResult();
        return number.longValue();
    }

    @Override
    public List<T> list() {
        return makeTypedQuery().getResultList();
    }

    @Override
    public List<T> list(int max) {
        return makeTypedQuery()
                .setMaxResults(max)
                .getResultList();
    }

    @Override
    public List<T> list(int first, int max) {
        return makeTypedQuery()
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    @Override
    public List<T> listDesc(String colName) {
        return makeTypedQueryDesc(colName).getResultList();
    }

    @Override
    public List<T> listDesc(String colName, int max) {
        return makeTypedQueryDesc(colName)
                .setMaxResults(max)
                .getResultList();
    }

    @Override
    public List<T> listDesc(String colName, int first, int max) {
        return makeTypedQueryDesc(colName)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    @Override
    public SearchResult<T> query(String hql, int pageId, int max) {
        Session session = getSession();
        TypedQuery countQuery = session.createQuery("select count(*) " + hql);
        Number number = (Number) countQuery.getSingleResult();
        int count = number.intValue();

        int start = (pageId - 1) * max;
        TypedQuery query = session.createQuery(hql);
        query.setFirstResult(start);
        query.setMaxResults(max);
        List<T> list = query.getResultList();

        return new SearchResult<>(start, max, count, list);
    }

}

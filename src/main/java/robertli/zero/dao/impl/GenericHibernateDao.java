/*
 * The MIT License
 *
 * Copyright 2016 Robert Li.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package robertli.zero.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import robertli.zero.dao.GenericDao;

/**
 *
 * @version 1.0 2016.09-17
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
        getSession().delete(get(id));
    }

    @Override
    public List<T> list() {
        return getSession().createQuery("from " + entityClass.getName()).list();
    }

    @Override
    public List<T> list(int max) {
        Session session = getSession();
        Query query = session.createQuery("from " + entityClass.getName());
        query.setMaxResults(max);
        return query.list();
    }

    @Override
    public List<T> list(int first, int max) {
        Session session = getSession();
        Query query = session.createQuery("from " + entityClass.getName());
        query.setFirstResult(first);
        query.setMaxResults(max);
        return query.list();
    }

    @Override
    public List<T> listDesc() {
        Session session = getSession();
        Query query = session.createQuery("from " + entityClass.getName() + " desc");
        return query.list();
    }

    @Override
    public List<T> listDesc(int max) {
        Session session = getSession();
        Query query = session.createQuery("from " + entityClass.getName() + " desc");
        query.setMaxResults(max);
        return query.list();
    }

    @Override
    public List<T> listDesc(int first, int max) {
        Session session = getSession();
        Query query = session.createQuery("from " + entityClass.getName() + " desc");
        query.setFirstResult(first);
        query.setMaxResults(max);
        return query.list();
    }

}

/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import java.io.Serializable;
import java.util.List;
import robertli.zero.dto.SearchResult;

/**
 *
 * @version 1.0.1 2016-12-28
 * @author Robert Li
 * @param <T> The Entity Class
 * @param <PK> The Type of ID
 */
public interface GenericDao<T extends Serializable, PK extends Serializable> {

    public T get(PK id);
    
    public T getLast(String colName);

    public T getForUpdate(PK id);

    public boolean isExist(PK id);

    public void save(T entity);

    public void saveOrUpdate(T entity);

    public void delete(T entity);

    public void deleteById(PK id);

    public List<T> list();

    public List<T> list(int max);

    public List<T> list(int first, int max);

    public List<T> listDesc(String colName);

    public List<T> listDesc(String colName, int max);

    public List<T> listDesc(String colName, int first, int max);

    public SearchResult<T> query(String hql, int pageId, int max);
}

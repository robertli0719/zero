/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @version 1.0.3 2017-03-28
 * @author Robert Li
 * @param <T> The Entity Class
 * @param <PK> The Type of ID
 */
public interface GenericDao<T extends Serializable, PK extends Serializable> {

    public T get(PK id);

    public T getForUpdate(PK id);

    public boolean isExist(PK id);

    public void save(T entity);

    public void saveOrUpdate(T entity);

    public void delete(T entity);

    public void deleteById(PK id);

    public int count();

    public List<T> list();

    public List<T> list(int limit);

    public List<T> list(int offset, int limit);

    public List<T> listDesc(String colName);

    public List<T> listDesc(String colName, int limit);

    public List<T> listDesc(String colName, int offset, int limit);

}

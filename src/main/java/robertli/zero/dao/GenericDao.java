package robertli.zero.dao;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @version 1.0 2016.09-17
 * @author Robert Li
 * @param <T> The Entity Class
 * @param <PK> The Type of ID
 */
public interface GenericDao<T extends Serializable, PK extends Serializable> {

    public T get(PK id);

    public T getForUpdate(PK id);

    public void save(T entity);

    public void saveOrUpdate(T entity);

    public void delete(T entity);

    public void deleteById(PK id);

    public List<T> list();

    public List<T> list(int max);

    public List<T> list(int first, int max);

    public List<T> listDesc();

    public List<T> listDesc(int max);

    public List<T> listDesc(int first, int max);
}

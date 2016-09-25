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

    public boolean isExist(PK id);

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

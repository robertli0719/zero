/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import java.util.List;
import robertli.zero.entity.Page;

/**
 *
 * @author Robert Li
 */
public interface PageDao extends GenericDao<Page, Integer>{
    
    public List<Page> listByCategory(String category);
}

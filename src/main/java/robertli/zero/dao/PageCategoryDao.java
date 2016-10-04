/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import java.util.List;
import robertli.zero.entity.PageCategory;

/**
 *
 * @author Robert Li
 */
public interface PageCategoryDao extends GenericDao<PageCategory, String> {

    public List<String> listName();
}

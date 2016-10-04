/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.List;
import robertli.zero.entity.Page;
import robertli.zero.entity.PageCategory;

/**
 *
 * @version 1.0.1 2016-10-03
 * @author Robert Li
 */
public interface PageService {

    public boolean addCategory(String name, String description);

    public boolean removeCategory(String name);

    public List<PageCategory> listCategory();

    public List<String> listCategoryName();

    public enum AddPageResult {
        SUCCESS, FAIL, NO_CATEGORY, NO_TITLE, NO_CONTENT
    }

    public AddPageResult addPage(String category, String title, String description, String content);

    public boolean removePage(int id);

    public void setStatus(int id, boolean opened);

    public boolean updatePage(int id, String title, String description, String content);

    public List<Page> listAll();

    public List<Page> listByCategory(String category);

    public Page getPage(int id);
}

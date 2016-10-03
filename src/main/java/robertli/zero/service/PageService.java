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
 * @version 1.0 2016-10-02
 * @author Robert Li
 */
public interface PageService {

    public boolean addCategory(String name, String description);

    public boolean removeCategory(String name);

    public List<PageCategory> listCategory();

    public boolean addPage(String category, String title, String description, String content);

    public void removePage(int id);

    public boolean updatePage(int id, String title, String description, String content);

    public List<Page> listAll();

    public List<Page> listAll(String category);

    public Page getPage(int id);
}

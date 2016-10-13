/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import java.util.List;
import robertli.zero.entity.LinkGroup;

/**
 *
 * @author Robert Li
 */
public interface LinkGroupDao extends GenericDao<LinkGroup, Integer> {

    public List<String> getNamespaceList();

    public List<String> getPageNameList(String namespace);

    public List<LinkGroup> getLinkGroupList(String namespace, String pageName);

    public List<String> getNameList(String namespace, String pageName);

    public boolean isExist(String namespace, String pageName, String name);

    public void addLinkGroup(String namespace, String pageName, String name, String comment, int picWidth, int picHeight);

    public void deleteLinkGroup(String namespace, String pageName, String name);

}

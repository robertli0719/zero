/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.List;
import robertli.zero.entity.LinkGroup;

/**
 *
 * @version 1.0 2016-10-12
 * @author Robert Li
 */
public interface LinkService {

    public List<String> getNamespaceList();

    public List<String> getPageNameList(String namespace);

    public List<LinkGroup> getLinkGroupList(String namespace, String pageName);

    public List<String> getNameList(String namespace, String pageName);

    public enum AddLinkGroupResult {
        SUCCESS, DATABASE_FAIL, DUPLICATE_GROUP, WRONG_WIDTH_ERROR, WRONG_HEIGHT_ERROR,
        NO_NAMESPACE_ERROR, NO_PAGE_NAME_ERROR, NO_NAME_ERROR, NO_COMMENT_ERROR
    }

    public AddLinkGroupResult addLinkGroup(String namespace, String pageName, String name, String comment, int picWidth, int picHeight);

    public void deleteLinkGroup(String namespace, String pageName, String name);
}

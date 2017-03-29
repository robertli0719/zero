/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.List;
import org.json.JSONObject;
import robertli.zero.entity.LinkGroup;

/**
 *
 * @version 1.0.3 2017-03-15
 * @author Robert Li
 */
public interface LinkService {

    public List<String> getNamespaceList();

    public List<String> getPageNameList(String namespace);

    public List<LinkGroup> getLinkGroupList(String namespace, String pageName);

    public List<String> getNameList(String namespace, String pageName);

    public void addLinkGroup(String namespace, String pageName, String name, String comment);

    public void deleteLinkGroup(String namespace, String pageName, String name);

    public void updateLinkGroupByJSON(JSONObject json);
}

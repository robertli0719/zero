/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import robertli.zero.dao.LinkGroupDao;
import robertli.zero.entity.Link;
import robertli.zero.entity.LinkGroup;
import robertli.zero.service.LinkService;

@Component("linkService")
public class LinkServiceImpl implements LinkService {

    @Resource
    private LinkGroupDao linkGroupDao;

    @Override
    public List<String> getNamespaceList() {
        return linkGroupDao.getNamespaceList();
    }

    @Override
    public List<String> getPageNameList(String namespace) {
        return linkGroupDao.getPageNameList(namespace);
    }

    @Override
    public List<LinkGroup> getLinkGroupList(String namespace, String pageName) {
        return linkGroupDao.getLinkGroupList(namespace, pageName);
    }

    @Override
    public List<String> getNameList(String namespace, String pageName) {
        return linkGroupDao.getNameList(namespace, pageName);
    }

    private AddLinkGroupResult validateAddLinkGroup(String namespace, String pageName, String name, String comment, int picWidth, int picHeight) {
        if (namespace == null || namespace.isEmpty()) {
            return AddLinkGroupResult.NO_NAMESPACE_ERROR;
        } else if (pageName == null || pageName.isEmpty()) {
            return AddLinkGroupResult.NO_PAGE_NAME_ERROR;
        } else if (name == null || name.isEmpty()) {
            return AddLinkGroupResult.NO_NAME_ERROR;
        } else if (comment == null || comment.isEmpty()) {
            return AddLinkGroupResult.NO_COMMENT_ERROR;
        } else if (picWidth < 0) {
            return AddLinkGroupResult.WRONG_WIDTH_ERROR;
        } else if (picHeight < 0) {
            return AddLinkGroupResult.WRONG_HEIGHT_ERROR;
        }
        return null;
    }

    @Override
    public AddLinkGroupResult addLinkGroup(String namespace, String pageName, String name, String comment, int picWidth, int picHeight) {
        AddLinkGroupResult result = validateAddLinkGroup(namespace, pageName, name, comment, picWidth, picHeight);
        if (result != null) {
            return result;
        }
        try {
            if (linkGroupDao.isExist(namespace, pageName, name)) {
                return AddLinkGroupResult.DUPLICATE_GROUP;
            }
            linkGroupDao.addLinkGroup(namespace, pageName, name, comment, picWidth, picHeight);
        } catch (RuntimeException re) {
            System.out.println(re);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AddLinkGroupResult.DATABASE_FAIL;
        }
        return AddLinkGroupResult.SUCCESS;
    }

    @Override
    public void deleteLinkGroup(String namespace, String pageName, String name) {
        linkGroupDao.deleteLinkGroup(namespace, pageName, name);
    }

    private JSONObject getLinkJSON(Link link) {
        JSONObject json = new JSONObject();
        json.put("id", link.getId());
        json.put("title", link.getTitle());
        json.put("comment", link.getComment());
        json.put("imgId", link.getImgId());
        json.put("imgUrl", link.getImgUrl());
        return json;
    }

    private JSONArray getLinkArrayJSON(List<Link> linkList) {
        if (linkList == null) {
            return new JSONArray();
        }
        JSONArray array = new JSONArray();
        for (Link link : linkList) {
            JSONObject obj = getLinkJSON(link);
            array.put(obj);
        }
        return array;
    }

    @Override
    public JSONObject getLinkGroupJSON(String namespace, String pageName, String name) {
        JSONObject json = new JSONObject();
        LinkGroup linkGroup = linkGroupDao.getLinkGroup(namespace, pageName, name);
        json.put("id", linkGroup.getId());
        json.put("namespace", linkGroup.getNamespace());
        json.put("pageName", linkGroup.getPageName());
        json.put("name", linkGroup.getName());
        json.put("comment", linkGroup.getComment());
        json.put("picWidth", linkGroup.getPicWidth());
        json.put("picHeight", linkGroup.getPicHeight());
        List<Link> linkList = linkGroup.getLinkList();
        JSONArray linkArray = getLinkArrayJSON(linkList);
        json.put("linkArray", linkArray);
        return json;
    }

    @Override
    public UpdateLinkGroupResult updateLinkGroupByJSON(JSONObject json) {
        try {
            String namespace = json.getString("namespace");
            String pageName = json.getString("pageName");
            String name = json.getString("name");
            LinkGroup linkGroup = linkGroupDao.getLinkGroup(namespace, pageName, name);
            linkGroup.getLinkList();
            //unfinish
            if (true) {
                throw new RuntimeException("Unfinish");
            }

        } catch (RuntimeException re) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return UpdateLinkGroupResult.DATABASE_FAIL;
        }
        return UpdateLinkGroupResult.SUCCESS;
    }

}

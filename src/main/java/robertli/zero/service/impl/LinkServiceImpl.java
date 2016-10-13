/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import robertli.zero.dao.LinkGroupDao;
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

}

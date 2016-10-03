/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import robertli.zero.dao.PageCategoryDao;
import robertli.zero.dao.PageDao;
import robertli.zero.dao.PageImageDao;
import robertli.zero.entity.Page;
import robertli.zero.entity.PageCategory;
import robertli.zero.service.PageService;

/**
 *
 * Unfinished
 *
 * @version 1.0 2016-10-02
 * @author robert
 */
@Component("pageService")
public class PageServiceImpl implements PageService {

    @Resource
    private PageCategoryDao pageCategoryDao;

    @Resource
    private PageDao pageDao;

    @Resource
    private PageImageDao pageImageDao;

    @Override
    public boolean addCategory(String name, String description) {
        PageCategory category = new PageCategory();
        category.setName(name);
        category.setDescription(description);
        try {
            pageCategoryDao.save(category);
        } catch (RuntimeException re) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return true;
        }
        return false;
    }

    @Override
    public boolean removeCategory(String name) {
        try {
            PageCategory category = pageCategoryDao.get(name);
            pageCategoryDao.delete(category);
        } catch (RuntimeException re) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return true;
        }
        return false;
    }

    @Override
    public List<PageCategory> listCategory() {
        return pageCategoryDao.list();
    }

    private void saveImage(String content) {
        /*
        仍在思考图片转存的问题，尚未完成
         */
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addPage(String category, String title, String description, String content) {
        saveImage(content);
        try {
            PageCategory pageCategory = pageCategoryDao.get(category);
            if (pageCategory == null) {
                throw new RuntimeException("no category");
            }
            Date now = new Date();
            Page page = new Page();
            page.setCategory(pageCategory);
            page.setTitle(title);
            page.setDescription(description);
            page.setContent(content);
            page.setCreateTime(now);
            page.setLastModifyTime(now);
        } catch (RuntimeException re) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return true;
        }
        return false;
    }

    @Override
    public void removePage(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setStatus(int id, boolean opened) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updatePage(int id, String title, String description, String content) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Page> listAll() {
        return pageDao.list();
    }

    @Override
    public List<Page> listAll(String category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page getPage(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import robertli.zero.core.WebConfiguration;
import robertli.zero.dao.PageCategoryDao;
import robertli.zero.dao.PageDao;
import robertli.zero.dao.PageImageDao;
import robertli.zero.entity.FileRecord;
import robertli.zero.entity.Page;
import robertli.zero.entity.PageCategory;
import robertli.zero.entity.PageImage;
import robertli.zero.service.PageService;
import robertli.zero.service.StorageService;

/**
 *
 * Unfinished
 *
 * @version 1.0 2016-10-03
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

    @Resource
    private StorageService storageService;

    @Resource
    private WebConfiguration webConfiguration;

    @Override
    public boolean addCategory(String name, String description) {
        try {
            if (pageCategoryDao.get(name) != null) {
                throw new RuntimeException("this category is existing.");
            }
            PageCategory category = new PageCategory();
            category.setName(name);
            category.setDescription(description);
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

    @Override
    public List<String> listCategoryName() {
        return pageCategoryDao.listName();
    }

    private void saveImage(Page page, String content) {
        String imageActionUrl = webConfiguration.getImageActionUrl();
        String patternStr = imageActionUrl + "\\?id=[0-9a-z]{8}(-[0-9a-z]{4}){3}-[0-9a-z]{12}";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String url = matcher.group();
            String uuid = url.split("id=")[1];
            FileRecord record = storageService.getFileRecord(uuid);
            if (record == null) {
                throw new RuntimeException("no image:" + uuid);
            } else if (record.isTemp()) {
                PageImage pImg = new PageImage();
                pImg.setImgId(uuid);
                pImg.setImgUrl(url);
                pImg.setPage(page);
                pageImageDao.save(pImg);
                storageService.fix(uuid);
            }
        }
    }

    @Override
    public AddPageResult addPage(String category, String title, String description, String content) {
        AddPageResult result = AddPageResult.FAIL;
        try {
            PageCategory pageCategory = pageCategoryDao.get(category);
            if (pageCategory == null) {
                result = AddPageResult.NO_CATEGORY;
                throw new RuntimeException("no category");
            } else if (title == null || title.length() == 0) {
                result = AddPageResult.NO_TITLE;
                throw new RuntimeException("no title");
            } else if (content == null || content.length() == 0) {
                result = AddPageResult.NO_CONTENT;
                throw new RuntimeException("no content");
            }
            Date now = new Date();
            Page page = new Page();
            page.setCategory(pageCategory);
            page.setTitle(title);
            page.setDescription(description);
            page.setContent(content);
            page.setCreateTime(now);
            page.setLastModifyTime(now);
            pageDao.save(page);
            saveImage(page, content);
            result = AddPageResult.SUCCESS;
        } catch (RuntimeException re) {
            System.out.println("Error when addPage:" + re.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return result;
    }

    @Override
    public boolean removePage(int id) {
        try {
            Page page = pageDao.get(id);
            for (PageImage image : page.getPageImageList()) {
                String uuid = image.getImgId();
                storageService.delete(uuid);
                pageImageDao.delete(image);
            }
            pageDao.delete(page);
        } catch (RuntimeException re) {
            System.out.println("Error when removePage:" + re.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return true;
        }
        return false;
    }

    @Override
    public void setStatus(int id, boolean opened) {
        pageDao.get(id).setOpened(opened);
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
    public List<Page> listByCategory(String category) {
        return pageDao.listByCategory(category);
    }

    @Override
    public Page getPage(int id) {
        Page page = pageDao.get(id);
        if (page != null && page.isOpened() == false) {
            return null;
        }
        return page;
    }

}

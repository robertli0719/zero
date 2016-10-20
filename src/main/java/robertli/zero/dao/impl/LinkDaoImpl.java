/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.dao.LinkDao;
import robertli.zero.dao.LinkGroupDao;
import robertli.zero.entity.Link;
import robertli.zero.entity.LinkGroup;

/**
 *
 * @author Robert Li
 */
@Component("linkDao")
public class LinkDaoImpl extends GenericHibernateDao<Link, Integer> implements LinkDao {

    @Resource
    private LinkGroupDao linkGroupDao;

    @Override
    public Link saveLink(String title, String url, String imgUrl, String imgId, String comment, int linkGroupId) {
        LinkGroup linkGroup = linkGroupDao.get(linkGroupId);

        Link link = new Link();
        link.setTitle(title);
        link.setUrl(url);
        link.setImgId(imgId);
        link.setImgUrl(imgUrl);
        link.setComment(comment);
        link.setLinkGroup(linkGroup);
        this.save(link);
        return link;
    }

}

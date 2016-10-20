/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import robertli.zero.entity.Link;

/**
 *
 * @author Robert Li
 */
public interface LinkDao extends GenericDao<Link, Integer> {

    public Link saveLink(String title, String url, String imgUrl, String imgId, String comment, int linkGroupId);
}

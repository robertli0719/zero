/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.UserPlatformDao;
import robertli.zero.entity.UserPlatform;

/**
 *
 * @author Robert Li
 */
@Component("userPlatformDao")
public class UserPlatformDaoImpl extends GenericHibernateDao<UserPlatform, String> implements UserPlatformDao {

}

/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import org.springframework.stereotype.Component;
import robertli.zero.dao.ChatChannelDao;
import robertli.zero.entity.ChatChannel;

/**
 *
 * @author Robert Li
 */
@Component("chatChannelDao")
public class ChatChannelDaoImpl extends GenericHibernateDao<ChatChannel, Integer> implements ChatChannelDao {

}

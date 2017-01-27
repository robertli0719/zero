/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao.impl;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import robertli.zero.dao.ChatMessageDao;
import robertli.zero.entity.ChatMessage;

/**
 *
 * @author Robert Li
 */
@Component("chatMessageDao")
public class ChatMessageDaoImpl extends GenericHibernateDao<ChatMessage, Integer> implements ChatMessageDao {

    @Resource
    private SessionFactory sessionFactory;

    @Override
    public List<ChatMessage> fetchChatChannel(int channelId, int max) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery query = session.createQuery("select m from ChatMessage m where m.chatChannel.id = :channelId order by m.id desc");
        query.setParameter("channelId", channelId);
        query.setMaxResults(max);
        return query.getResultList();
    }

}

/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dao;

import java.util.List;
import robertli.zero.entity.ChatMessage;

/**
 *
 * @author Robert Li
 */
public interface ChatMessageDao extends GenericDao<ChatMessage, Integer> {

    public List<ChatMessage> fetchChatChannel(int channelId, int max);
}

/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service;

import java.util.List;
import robertli.zero.dto.ChatChannelDto;
import robertli.zero.dto.ChatMessageDto;

/**
 *
 * @version v1.0 2017-01-27
 * @author Robert Li
 */
public interface ChatService {

    public List<ChatChannelDto> listChatChannel();

    public ChatChannelDto getChatChannel(int id);

    public List<ChatMessageDto> fetchChatChannel(int channelId, int max);

    public void postMessage(ChatMessageDto chatMessageDto);
    
}

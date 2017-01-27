/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.dao.ChatChannelDao;
import robertli.zero.dao.ChatMessageDao;
import robertli.zero.dto.ChatChannelDto;
import robertli.zero.dto.ChatMessageDto;
import robertli.zero.entity.ChatChannel;
import robertli.zero.service.ChatService;

/**
 *
 * @author Robert Li
 */
@Component("chatService")
public class ChatServiceImpl implements ChatService {

    @Resource
    private ChatChannelDao chatChannelDao;

    @Resource
    private ChatMessageDao chatMessageDao;

    private ChatChannelDto mapChanelToDto(ChatChannel chatChannel) {
        ChatChannelDto dto = new ChatChannelDto();
        dto.setId(chatChannel.getId());
        dto.setName(chatChannel.getName());
        dto.setCreatedDate(chatChannel.getCreatedDate());
        return dto;
    }

    private List<ChatChannelDto> mapChanelListToDtoList(List<ChatChannel> channelList) {
        List<ChatChannelDto> dtoList = new ArrayList<>();
        for (ChatChannel channel : channelList) {
            dtoList.add(mapChanelToDto(channel));
        }
        return dtoList;
    }

    @Override
    public List<ChatChannelDto> listChatChannel() {
        return mapChanelListToDtoList(chatChannelDao.list());
    }

    @Override
    public ChatChannelDto getChatChannel(int id) {
        ChatChannel channel = chatChannelDao.get(id);
        return mapChanelToDto(channel);
    }

    @Override
    public List<ChatMessageDto> fetchChatChannel(int channelId, int max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void postMessage(ChatMessageDto chatMessageDto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

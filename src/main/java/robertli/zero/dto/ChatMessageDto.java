/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.dto;

import java.util.Date;

/**
 *
 * @author Robert Li
 */
public class ChatMessageDto {

    private Integer id;
    private int userId;
    private String name;
    private String content;
    private Date createdDate;
    private int chatChannelId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getChatChannelId() {
        return chatChannelId;
    }

    public void setChatChannelId(int chatChannelId) {
        this.chatChannelId = chatChannelId;
    }

}

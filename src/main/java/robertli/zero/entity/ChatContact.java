/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Robert Li
 */
@Entity
@Table(name = "chat_contact", indexes = {})
public class ChatContact implements Serializable {

    private int id;
    private String name;
    private User owner;
    private ChatChannel chatChannel;
    private int lastReadMessageId;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(nullable = false)
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @ManyToOne
    @JoinColumn(nullable = false)
    public ChatChannel getChatChannel() {
        return chatChannel;
    }

    public void setChatChannel(ChatChannel chatChannel) {
        this.chatChannel = chatChannel;
    }

    public int getLastReadMessageId() {
        return lastReadMessageId;
    }

    public void setLastReadMessageId(int lastReadMessageId) {
        this.lastReadMessageId = lastReadMessageId;
    }

}

package fr.lpacsid.chat.beans;

import java.time.LocalDateTime;

public class Message {
    private Integer id;
    private Integer conversation;
    private Integer sender;
    private String dateSent;
    private String content;
    private String senderName;

    public Message(Integer conversation, Integer sender, String content) {
        this.conversation = conversation;
        this.sender = sender;
        this.content = content;
        this.initDateSent();
    }

    public Message(Integer conversation, Integer sender, String dateSent, String content) {
        this.conversation = conversation;
        this.sender = sender;
        this.dateSent = dateSent;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public Integer getConversation() {
        return conversation;
    }

    public void setConversation(Integer conversation) {
        this.conversation = conversation;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public String getContent() {
        return content;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void initDateSent() {
        this.dateSent = LocalDateTime.now().toString(); //  2023-06-01T00:31:15.116789
    }
}

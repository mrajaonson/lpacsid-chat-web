package fr.lpacsid.chat.beans;

import fr.lpacsid.chat.utils.DateUtility;

public class Message {
    private Integer id;
    private Integer conversation;
    private User sender;
    private String date;
    private String content;

    public Message(Integer conversation, User sender, String content) {
        this.conversation = conversation;
        this.sender = sender;
        this.content = content;
        this.initDate();
    }

    public Message(Integer conversation, User sender, String date, String content) {
        this.conversation = conversation;
        this.sender = sender;
        this.date = date;
        this.content = content;
    }

    public Message() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConversation() {
        return conversation;
    }

    public void setConversation(Integer conversation) {
        this.conversation = conversation;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void initDate() {
        this.date = DateUtility.getLocalDateTimeNowToString();
    }
}

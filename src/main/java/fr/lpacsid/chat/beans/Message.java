package fr.lpacsid.chat.beans;

import fr.lpacsid.chat.utils.DateUtility;

public class Message {
    private Integer id;
    private Integer conversation;
    private User sender;
    private String date;
    private String modification;
    private String content;
    private String formattedDate;

    public Message(Integer conversation, User sender, String content) {
        this.conversation = conversation;
        this.sender = sender;
        this.content = content;
        this.initDate();
        this.modification = "";
    }

    public Message(Integer conversation, User sender, String date, String modification, String content) {
        this.conversation = conversation;
        this.sender = sender;
        this.date = date;
        this.modification = modification;
        this.content = content.trim();
        this.formattedDate = DateUtility.getFormattedDate(date);
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

    public String getModification() {
        return this.modification;
    }

    public void setModification(String date) {
        this.modification = date;
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

    public String getFormattedDate() {
        return this.formattedDate;
    }

    public void setFormattedDate(String date) {
        this.formattedDate = date;
    }
}

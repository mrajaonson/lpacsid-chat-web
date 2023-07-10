package fr.lpacsid.chat.beans;

import fr.lpacsid.chat.utils.DateUtility;

public class Participation {
    private Integer id;
    private Integer conversation;
    private String addDate;
    private final User user;

    public Integer getId() {
        return id;
    }

    public Integer getConversation() {
        return conversation;
    }

    public void setConversation(Integer conversation) {
        this.conversation = conversation;
    }

    public User getUser() {
        return user;
    }

    public String getAddDate() {
        return addDate;
    }

    public void initAddDate() {
        this.addDate = DateUtility.getLocalDateTimeNowToString();
    }

    public Participation(Integer conversation, User user) {
        this.conversation = conversation;
        this.user = user;
        this.initAddDate();
    }

    public Participation(Integer id, Integer conversation, User user, String addDate) {
        this.id = id;
        this.conversation = conversation;
        this.user = user;
        this.addDate = addDate;
    }
}

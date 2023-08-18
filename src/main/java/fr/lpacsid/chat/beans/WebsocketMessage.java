package fr.lpacsid.chat.beans;

import fr.lpacsid.chat.enums.ConversationTypes;
import fr.lpacsid.chat.utils.DateUtility;

public class WebsocketMessage {
    private User sender;
    private Integer conversation;
    private String type;
    private String[] participations;
    private String content;
    private String date;
    private String conversationLabel;

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Integer getConversation() {
        return conversation;
    }

    public void setConversation(Integer conversation) {
        this.conversation = conversation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getParticipations() {
        return participations;
    }

    public void setParticipations(String[] participations) {
        this.participations = participations;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void initDate() {
        this.date = DateUtility.getLocalDateTimeNowToString();
    }

    public String getConversationLabel() {
        return conversationLabel;
    }

    public void setConversationLabel(String conversationLabel) {
        this.conversationLabel = conversationLabel;
    }

    public ConversationTypes getConversationTypesFromTypeString () {
        return ConversationTypes.valueOf(this.type);
    }
}

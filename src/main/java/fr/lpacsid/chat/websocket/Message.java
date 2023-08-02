package fr.lpacsid.chat.websocket;

import fr.lpacsid.chat.utils.DateUtility;

public class Message {
    private String from;
    private String to;
    private String content;
    private String date;
    private String type;

    @Override
    public String toString() {
        return super.toString();
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void initDate() {
        this.date = DateUtility.getLocalDateTimeNowToString();
    }
}

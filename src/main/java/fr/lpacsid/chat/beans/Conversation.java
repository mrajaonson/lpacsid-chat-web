package fr.lpacsid.chat.beans;

import fr.lpacsid.chat.enums.ConversationTypes;
import fr.lpacsid.chat.utils.DateUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Conversation {
    private Integer id;
    private User admin;
    private String creationDate;
    private List<Participation> participations = new ArrayList<>();
    private ConversationTypes type;
    private List<Message> messages = new ArrayList<>();
    private String label;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }

    public ConversationTypes getType() {
        return type;
    }

    public void setType(ConversationTypes type) {
        this.type = type;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTypeString() {
        return this.type.toString();
    }

    public void setTypeFromStringValue(String type) {
        this.type = ConversationTypes.valueOf(type);
    }

    public void initCreationDate() {
        this.creationDate = DateUtility.getLocalDateTimeNowToString();
    }

    public Conversation(Integer id, User admin, String creationDate, List<Participation> participations, String label, String type) {
        this.id = id;
        this.admin = admin;
        this.creationDate = creationDate;
        this.participations = participations;
        this.label = label;
        this.setTypeFromStringValue(type);
    }

    public Conversation(User admin, ConversationTypes type) {
        this.admin = admin;
        this.initCreationDate();
        this.addParticipant(admin);
        this.label = "";
        this.type = type;
    }

    public void addParticipant(User user) {
        Participation participation = new Participation(this.id, user);
        this.participations.add(participation);
    }

    public User getDiscussionParticipant(Integer userAdminId) {
        Participation participation = this.participations.stream()
                .filter(i -> !Objects.equals(i.getUser().getId(), userAdminId))
                .findFirst()
                .orElse(null);

        if (participation != null) {
            return participation.getUser();
        }
        return null;
    }
}

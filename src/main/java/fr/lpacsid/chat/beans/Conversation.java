package fr.lpacsid.chat.beans;

import fr.lpacsid.chat.enums.ConversationTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Conversation {
    private Integer id;
    private User admin;
    private String creationDate;
    private List<Participant> participants = new ArrayList<>();
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

    public List<Participant> getParticipations() {
        return participants;
    }

    public void setParticipations(List<Participant> participants) {
        this.participants = participants;
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
        this.creationDate = LocalDateTime.now().toString(); //  2023-06-01T00:31:15.116789
    }

    public Conversation(Integer id, User admin, String creationDate, List<Participant> participants, String label, String type) {
        this.id = id;
        this.admin = admin;
        this.creationDate = creationDate;
        this.participants = participants;
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
        Participant participant = new Participant(this.id, user);
        this.participants.add(participant);
    }

    public User getDiscussionParticipant(Integer userAdminId) {
        Participant participant = this.participants.stream()
                .filter(i -> !Objects.equals(i.getUser().getId(), userAdminId))
                .findFirst()
                .orElse(null);

        assert participant != null;
        return participant.getUser();
    }
}

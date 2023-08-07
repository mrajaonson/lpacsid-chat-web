package fr.lpacsid.chat.beans;

import fr.lpacsid.chat.enums.ConversationTypes;
import fr.lpacsid.chat.enums.RoleConversation;
import fr.lpacsid.chat.utils.DateUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Conversation {
    private Integer id;
    private User prime;
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

    public User getPrime() {
        return prime;
    }

    public void setPrime(User prime) {
        this.prime = prime;
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

    public Boolean isChannel() {
        return this.type.equals(ConversationTypes.CHANNEL);
    }

    public Boolean isGroup() {
        return this.type.equals(ConversationTypes.GROUP);
    }

    public Boolean isDiscussion() {
        return this.type.equals(ConversationTypes.DISCUSSION);
    }

    public Conversation(Integer id, User prime, String creationDate, List<Participation> participations, String label, String type) {
        this.id = id;
        this.prime = prime;
        this.creationDate = creationDate;
        this.participations = participations;
        this.label = label;
        this.setTypeFromStringValue(type);
    }

    public Conversation(User prime, ConversationTypes type) {
        this.prime = prime;
        this.initCreationDate();
        this.addModerator(prime);
        this.type = type;
    }

    public void addParticipant(User user) {
        Participation participation = new Participation(this.id, user, RoleConversation.PARTICIPANT);
        this.participations.add(participation);
    }

    public void addModerator(User user) {
        Participation participation = new Participation(this.id, user, RoleConversation.MODERATOR);
        this.participations.add(participation);
    }

    public void setDiscussionLabel(Integer userId) {
        if (this.isDiscussion()) {
            Participation participation = this.participations.stream()
                    .filter(i -> !Objects.equals(i.getUser().getId(), userId))
                    .findFirst()
                    .orElse(null);

            if (participation != null) {
                this.label = participation.getUser().getUsername();
            }
        } else if (this.isChannel() && this.label.isEmpty()) {
            this.label = ConversationTypes.CHANNEL.getFr() + " #" +this.id;
        } else if (this.isGroup() && this.label.isEmpty()) {
            this.label = ConversationTypes.GROUP.getFr() + " #" +this.id;
        }
    }
}

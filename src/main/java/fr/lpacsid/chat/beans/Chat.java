package fr.lpacsid.chat.beans;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Chat {
    private ArrayList<Participant> participants;

    private String creationDate;

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<Participant> participants) {
        this.participants = participants;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Chat() {
        this.participants = new ArrayList<Participant>();
        this.creationDate = LocalDateTime.now().toString(); //  2023-06-01T00:31:15.116789
    }

    public void addParticipant(Participant participant) {
        this.participants.add(participant);
    }
}

package fr.lpacsid.chat.beans;

public class Participant {
    private Number id;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public Participant(Number id, String username) {
        this.id = id;
        this.username = username;
    }
}

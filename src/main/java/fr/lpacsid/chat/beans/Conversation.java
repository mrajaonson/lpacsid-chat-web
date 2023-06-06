package fr.lpacsid.chat.beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Conversation {
    private Integer id;
    private List<User> users;

    private String creationDate;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void initCreationDate() {
        this.creationDate = LocalDateTime.now().toString(); //  2023-06-01T00:31:15.116789
    }

    public Conversation() {
        this.users = new ArrayList<>();
        this.initCreationDate();
    }

    public Conversation(User u1, User u2, String creationDate) {
        this.users = new ArrayList<>();
        this.creationDate = creationDate;
        this.addUser(u1);
        this.addUser(u2);
    }

    public Conversation(User u1, User u2) {
        this.users = new ArrayList<>();
        this.addUser(u1);
        this.addUser(u2);
        this.initCreationDate();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public Integer getUserIdByIndex(Integer i) {
        return this.users.get(i).getId();
    }

    public String getUserNameByIndex(Integer i) {
        return this.users.get(i).getLogin();
    }
}

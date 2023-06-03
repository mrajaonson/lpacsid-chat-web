package fr.lpacsid.chat.beans;

import fr.lpacsid.chat.UserStatus;

import java.time.LocalDateTime;

public class User {
    private Integer id;
    private String login;
    private String password;
    private String creationDate;
    private String status;
    private String lastConnection;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(String lastConnection) {
        this.lastConnection = lastConnection;
    }

    public void initCreationDate() {
        this.creationDate = LocalDateTime.now().toString(); //  2023-06-01T00:31:15.116789
    }

    public void initLastConnection() {
        this.creationDate = LocalDateTime.now().toString(); //  2023-06-01T00:31:15.116789
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.status = UserStatus.OFFLINE.toString();
        this.initCreationDate();
        this.initLastConnection();
    }

    public User(String login) {
        this.login = login;
    }

    public User(Integer id, String login, String creationDate, String status, String lastConnection) {
        this.id = id;
        this.login = login;
        this.creationDate = creationDate;
        this.status = status;
        this.lastConnection = lastConnection;
    }
}

package fr.lpacsid.chat.beans;

import fr.lpacsid.chat.enums.UserStatus;
import fr.lpacsid.chat.utils.DateUtility;

import java.util.logging.Level;
import java.util.logging.Logger;


public class User {
    private Integer id;
    private String login;
    private String password;
    private String creationDate;
    private UserStatus status;
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setStatusFromStringValue(String status) {
        try {
            this.status = UserStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            this.status = UserStatus.OFFLINE;
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String getStatusString() {
        return this.status.toString();
    }

    public String getFrenchStatus() {
        return this.status.getFr();
    }

    public String getEnglishStatus() {
        return this.status.getEn();
    }

    public String getLastConnection() {
        return lastConnection;
    }

    public void setLastConnection(String lastConnection) {
        this.lastConnection = lastConnection;
    }

    public void initCreationDate() {
        this.creationDate = DateUtility.getLocalDateTimeNowToString();
    }

    public void initLastConnection() {
        this.lastConnection = DateUtility.getLocalDateTimeNowToString();
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.initCreationDate();
        this.initLastConnection();
        this.status = UserStatus.OFFLINE;
    }

    public User(String login) {
        this.login = login;
    }

    public User(Integer id, String login, String creationDate, String status, String lastConnection) {
        this.id = id;
        this.login = login;
        this.creationDate = creationDate;
        this.setStatusFromStringValue(status);
        this.lastConnection = lastConnection;
    }
}

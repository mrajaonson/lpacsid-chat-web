package fr.lpacsid.chat.beans;

import fr.lpacsid.chat.enums.UserStatus;
import fr.lpacsid.chat.utils.DateUtility;
import fr.lpacsid.chat.utils.LoggerUtility;

public class User {
    private Integer id;
    private String username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
            LoggerUtility.logException(e);
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

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.initCreationDate();
        this.initLastConnection();
        this.status = UserStatus.OFFLINE;
    }

    public User(String username) {
        this.username = username;
    }

    public User(Integer id, String username, String creationDate, String status, String lastConnection) {
        this.id = id;
        this.username = username;
        this.creationDate = creationDate;
        this.setStatusFromStringValue(status);
        this.lastConnection = lastConnection;
    }
}

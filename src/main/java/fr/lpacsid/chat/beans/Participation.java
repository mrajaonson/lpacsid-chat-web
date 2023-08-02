package fr.lpacsid.chat.beans;

import fr.lpacsid.chat.enums.RoleConversation;
import fr.lpacsid.chat.utils.DateUtility;

import java.util.Objects;

public class Participation {
    private Integer id;
    private Integer conversation;
    private String addDate;
    private User user;
    private RoleConversation role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConversation() {
        return conversation;
    }

    public void setConversation(Integer conversation) {
        this.conversation = conversation;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public void initAddDate() {
        this.addDate = DateUtility.getLocalDateTimeNowToString();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RoleConversation getRole() {
        return this.role;
    }

    public void setRole(RoleConversation role) {
        this.role = role;
    }

    public String getStringRole() {
        return this.role.toString();
    }

    public void setRoleFromStringValue(String role) {
        if (Objects.equals(role, "")) {
            this.role = RoleConversation.PARTICIPANT;
        } else {
            this.role = RoleConversation.valueOf(role);
        }
    }

    public Participation(Integer conversation, User user, RoleConversation role) {
        this.conversation = conversation;
        this.user = user;
        this.initAddDate();
        this.setRole(role);
    }

    public Participation(Integer id, Integer conversation, User user, String addDate, String role) {
        this.id = id;
        this.conversation = conversation;
        this.user = user;
        this.addDate = addDate;
        this.setRoleFromStringValue(role);
    }
}

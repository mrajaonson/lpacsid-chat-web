package fr.lpacsid.chat.beans;

public class User {
    private Number id;
    private String login;
    private String password;

    private String username;

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login) {
        this.login = login;
    }

    public void initializeUsername() {
        this.username = this.login + '#' + this.id.toString();
    }
}

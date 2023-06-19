package fr.lpacsid.chat.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    private final String url;
    private final String username;
    private final String password;

    public DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DaoFactory getInstance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            String urlDB = "jdbc:mysql://localhost:3306/chat?allowPublicKeyRetrieval=true&useSSL=false";
            String userDB = "chat";
            String passDB = "chat123";

            return new DaoFactory(urlDB, userDB, passDB);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public UserDao getUserDao() {
        return new UserDaoImpl(this);
    }

    public ConversationDao getConversationDao() {
        return new ConversationDaoImpl(this);
    }

    public MessageDao getMessageDao() {
        return new MessageDaoImpl(this);
    }

    public ParticipantDao getParticipationDao() {
        return new ParticipantDaoImpl(this);
    }
}

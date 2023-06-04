package fr.lpacsid.chat.db;

import fr.lpacsid.chat.beans.Conversation;
import fr.lpacsid.chat.beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConversationDB {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private String query;

    private void getConnection() throws SQLException {
        ConnectDB connectDB = new ConnectDB();
        this.connection = connectDB.getConnection();
    }

    private void closeConnection() throws SQLException {
        this.preparedStatement.close();
        this.connection.close();
    }

    public void createConversation(Conversation conversation) throws SQLException {
        try {
            this.getConnection();
            this.query = "INSERT INTO conversations(user1, user2, creationDate) VALUES(?, ?, ?)";
            this.preparedStatement = this.connection.prepareStatement(this.query);

            this.preparedStatement.setInt(1, conversation.getUserIdByIndex(0));
            this.preparedStatement.setInt(2, conversation.getUserIdByIndex(1));
            this.preparedStatement.setString(3, conversation.getCreationDate());

            this.preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
    }

    public ArrayList<Conversation> readAllUserConversations(String username) throws SQLException {
        try {
            this.getConnection();
            ArrayList<Conversation> conversations = new ArrayList<>();

            this.query = "SELECT * FROM conversations WHERE user1 = ? OR user2 = ?";
            this.preparedStatement = this.connection.prepareStatement(this.query);

            UserDB userDB = new UserDB();
            User user = userDB.readUser(username);

            this.preparedStatement.setInt(1, user.getId());
            this.preparedStatement.setInt(2, user.getId());

            ResultSet resultSet = this.preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id_u1 = resultSet.getInt("user1");
                Integer id_u2 = resultSet.getInt("user2");
                String creationDate = resultSet.getString("creationDate");

                User u1 = userDB.readUserById(id_u1);
                User u2 = userDB.readUserById(id_u2);

                conversations.add(new Conversation(u1, u2, creationDate));
            }
            return conversations;
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return null;
    }

}

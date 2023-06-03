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
    public void createConversation(Conversation conversation) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement;
        try {
            ConnectDB connectDB = new ConnectDB();
            connection = connectDB.getConnection();
            String query = "INSERT INTO conversations(user1, user2, creationDate) VALUES(?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, conversation.getUserIdByIndex(0));
            preparedStatement.setInt(2, conversation.getUserIdByIndex(1));
            preparedStatement.setString(3, conversation.getCreationDate());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public ArrayList<Conversation> readAllUserConversations(String username) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            ConnectDB connectDB = new ConnectDB();
            connection = connectDB.getConnection();

            ArrayList<Conversation> conversations = new ArrayList<>();

            String query = "SELECT * FROM conversations WHERE user1 = ? OR user2 = ?";
            preparedStatement = connection.prepareStatement(query);

            UserDB userDB = new UserDB();
            User user = userDB.readUser(username);

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
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
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return null;
    }

}

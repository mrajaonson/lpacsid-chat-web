package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.Conversation;
import fr.lpacsid.chat.beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConversationDaoImpl implements ConversationDao {

    private final DaoFactory daoFactory;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public ConversationDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private void getConnection() throws SQLException {
        this.connection = this.daoFactory.getConnection();
    }

    private void closeConnection() throws SQLException {
        this.preparedStatement.close();
        this.connection.close();
    }

    @Override
    public void createConversation(Conversation conversation) throws SQLException {
        try {
            this.getConnection();
            String query = "INSERT INTO conversations(user1, user2, creationDate) VALUES(?, ?, ?)";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, conversation.getUserIdByIndex(0));
            this.preparedStatement.setInt(2, conversation.getUserIdByIndex(1));
            this.preparedStatement.setString(3, conversation.getCreationDate());

            this.preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(ConversationDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public Conversation readConversation(Integer id) throws SQLException {
        try {
            this.getConnection();
            String query = "SELECT * FROM conversations WHERE id = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, id);

            UserDaoImpl userDao = new UserDaoImpl(daoFactory);

            ResultSet resultSet = this.preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer id_u1 = resultSet.getInt("user1");
                Integer id_u2 = resultSet.getInt("user2");
                String creationDate = resultSet.getString("creationDate");

                User u1 = userDao.readUserById(id_u1);
                User u2 = userDao.readUserById(id_u2);

                return new Conversation(id, u1, u2, creationDate);
            }
        } catch (SQLException e) {
            Logger.getLogger(ConversationDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return null;
    }

    @Override
    public void updateConversation(Integer id) throws SQLException {

    }

    @Override
    public void deleteConversation(Integer id) throws SQLException {

    }

    @Override
    public List<Conversation> readAllUserConversations(String username) throws SQLException {
        try {
            this.getConnection();
            List<Conversation> conversations = new ArrayList<>();

            String query = "SELECT * FROM conversations WHERE user1 = ? OR user2 = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            UserDaoImpl userDao = new UserDaoImpl(daoFactory);
            User user = userDao.readUser(username);

            this.preparedStatement.setInt(1, user.getId());
            this.preparedStatement.setInt(2, user.getId());

            ResultSet resultSet = this.preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer id_u1 = resultSet.getInt("user1");
                Integer id_u2 = resultSet.getInt("user2");
                String creationDate = resultSet.getString("creationDate");

                User u1 = userDao.readUserById(id_u1);
                User u2 = userDao.readUserById(id_u2);

                conversations.add(new Conversation(id, u1, u2, creationDate));
            }
            return conversations;
        } catch (SQLException e) {
            Logger.getLogger(ConversationDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return null;
    }
}

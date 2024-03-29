package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.Message;
import fr.lpacsid.chat.beans.User;
import fr.lpacsid.chat.utils.LoggerUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class MessageDaoImpl implements MessageDao {

    private final DaoFactory daoFactory;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public MessageDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    private void getConnection() throws SQLException {
        this.connection = this.daoFactory.getConnection();
    }

    private void closeConnection() throws SQLException {
        if (this.preparedStatement != null) {
            this.preparedStatement.close();
        }
        if (this.connection != null) {
            this.connection.close();
        }
    }

    @Override
    public Integer createMessage(Message message) throws SQLException {
        Integer insertedId = null;
        try {
            this.getConnection();
            String query = "INSERT INTO messages(conversation, sender, date, modification, content) VALUES (?,?,?,?,?)";
            this.preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            this.preparedStatement.setInt(1, message.getConversation());
            this.preparedStatement.setInt(2, message.getSender().getId());
            this.preparedStatement.setString(3, message.getDate());
            this.preparedStatement.setString(4, message.getModification());
            this.preparedStatement.setString(5, message.getContent());

            this.preparedStatement.executeUpdate();

            ResultSet generatedKeys = this.preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                insertedId = generatedKeys.getInt(1);
                LoggerUtility.logInsertQuery("MESSAGE", insertedId);
            }

            return insertedId;
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        }  finally {
            this.closeConnection();
        }
        return insertedId;
    }

    @Override
    public Message readMessage(Integer id) throws SQLException {
        try {
            this.getConnection();
            String query = "SELECT * FROM messages WHERE id = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, id);
            ResultSet resultSet = this.preparedStatement.executeQuery();

            UserDaoImpl userDao = new UserDaoImpl(daoFactory);

            if (resultSet.next()) {
                Integer conversation = resultSet.getInt("conversation");
                Integer sender = resultSet.getInt("sender");
                String date = resultSet.getString("date");
                String modification = resultSet.getString("modification");
                String content = resultSet.getString("content");

                User user = userDao.readUserById(sender);

                return new Message(conversation, user, date, modification, content);
            }
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }

    @Override
    public void updateMessage(Message message) throws SQLException {

    }

    @Override
    public void deleteMessage(Integer id) throws SQLException {
        try {
            this.getConnection();
            String query = "DELETE FROM messages WHERE id = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, id);

            int rowAffected = this.preparedStatement.executeUpdate();
            LoggerUtility.logDeleteQuery("messages", rowAffected, id);
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public void deleteMessagesByConversationId(Integer id) throws SQLException {
        try {
            this.getConnection();
            String query = "DELETE FROM messages WHERE conversation = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, id);

            int rowAffected = this.preparedStatement.executeUpdate();
            LoggerUtility.logDeleteQuery("messages", rowAffected, id);
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public List<Message> readAllConversationMessages(Integer conversation) throws SQLException {
        try {
            this.getConnection();
            List<Message> messages = new ArrayList<>();

            String query = "SELECT * FROM messages WHERE conversation = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, conversation);

            ResultSet resultSet = this.preparedStatement.executeQuery();

            UserDaoImpl userDao = new UserDaoImpl(daoFactory);

            while (resultSet.next()) {
                Integer sender = resultSet.getInt("sender");
                String date = resultSet.getString("date");
                String modification = resultSet.getString("modification");
                String content = resultSet.getString("content");

                User user = userDao.readUserById(sender);
                Message message = new Message(conversation, user, date, modification, content);

                messages.add(message);
            }

            return messages;
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }
}

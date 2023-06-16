package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.Message;
import fr.lpacsid.chat.beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private void logErrorException(Exception e) {
        Logger.getLogger(MessageDaoImpl.class.getName()).log(Level.SEVERE, null, e);
    }

    @Override
    public void createMessage(Message message) throws SQLException {
        try {
            this.getConnection();
            String query = "INSERT INTO messages(conversation, sender, dateSent, content) VALUES (?,?,?,?)";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, message.getConversation());
            this.preparedStatement.setInt(2, message.getSender());
            this.preparedStatement.setString(3, message.getDateSent());
            this.preparedStatement.setString(4, message.getContent());

            this.preparedStatement.executeUpdate();
        } catch (SQLException e) {
            this.logErrorException(e);
        }  finally {
            this.closeConnection();
        }
    }

    @Override
    public Message readMessage(Integer id) throws SQLException {
        try {
            this.getConnection();
            String query = "SELECT * FROM messages WHERE id = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, id);
            ResultSet resultSet = this.preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer conversation = resultSet.getInt("conversation");
                Integer sender = resultSet.getInt("sender");
                String dateSent = resultSet.getString("dateSent");
                String content = resultSet.getString("content");

                return new Message(conversation, sender, dateSent, content);
            }
        } catch (SQLException e) {
            this.logErrorException(e);
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

    }

    @Override
    public List<Message> readAllConversationMessages(Integer conversation) throws SQLException {
        try {
            this.getConnection();
            List<Message> messages = new ArrayList<>();

            String query = "SELECT * FROM messages WHERE conversation = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, conversation);

            UserDaoImpl userDao = new UserDaoImpl(daoFactory);

            ResultSet resultSet = this.preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer sender = resultSet.getInt("sender");
                String dateSent = resultSet.getString("dateSent");
                String content = resultSet.getString("content");

                User user = userDao.readUserById(sender);
                Message message = new Message(conversation, sender, dateSent, content);
                message.setSenderName(user.getLogin());

                messages.add(message);
            }

            return messages;
        } catch (SQLException e) {
            this.logErrorException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }
}

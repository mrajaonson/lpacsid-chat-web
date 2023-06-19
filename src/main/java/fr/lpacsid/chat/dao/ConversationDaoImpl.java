package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.Conversation;
import fr.lpacsid.chat.beans.Participant;
import fr.lpacsid.chat.beans.User;
import fr.lpacsid.chat.enums.ConversationTypes;

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
    private final ParticipantDao participationDao;
    private final UserDao userDao;

    public ConversationDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.participationDao = this.daoFactory.getParticipationDao();
        this.userDao = this.daoFactory.getUserDao();
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
    public void createConversation(Conversation conversation) throws SQLException {
        try {
            this.getConnection();
            String query = "INSERT INTO conversations(admin, creationDate, label, type) VALUES(?, ?, ?, ?)";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, conversation.getAdmin().getId());
            this.preparedStatement.setString(2, conversation.getCreationDate());
            this.preparedStatement.setString(3, conversation.getLabel());
            this.preparedStatement.setString(4, conversation.getTypeString());

            this.preparedStatement.executeUpdate();

            // Create participants
            Integer conversationId = this.getConversationId(conversation);
            for (Participant participant : conversation.getParticipations()) {
                participant.setConversation(conversationId);
                this.participationDao.createParticipant(participant);
            }
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

            ResultSet resultSet = this.preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer adminId = resultSet.getInt("admin");
                String creationDate = resultSet.getString("creationDate");
                String label = resultSet.getString("label");
                String type = resultSet.getString("type");

                User admin = this.userDao.readUserById(adminId);
                List<Participant> participants = this.participationDao.readAllConversationParticipants(id);

                return new Conversation(id, admin, creationDate, participants, label, type);
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
    public List<Conversation> readAllUserConversations(Integer userId) throws SQLException {
        try {
            this.getConnection();
            List<Conversation> conversations = new ArrayList<>();
            List<Integer> converationsId = this.readAllUserConversationsId(userId);

            for (Integer conversationId : converationsId) {
                Conversation conversation = this.readConversation(conversationId);
                if (conversation.getType() == ConversationTypes.DISCUSSION) {
                    User participant = conversation.getDiscussionParticipant(userId);
                    conversation.setLabel(participant.getLogin());
                }
                conversations.add(conversation);
            }
            return conversations;
        } catch (SQLException e) {
            Logger.getLogger(ConversationDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return null;
    }

    @Override
    public List<Integer> readAllUserConversationsId(Integer userId) throws SQLException {
        try {
            this.getConnection();

            List<Integer> userConversationsId = new ArrayList<>();

            String query = "SELECT * FROM participants WHERE user = ? ";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, userId);

            ResultSet resultSet = this.preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer conversationId = resultSet.getInt("conversation");
                if (!userConversationsId.contains(conversationId)) {
                    userConversationsId.add(conversationId);
                }
            }

            return userConversationsId;
        } catch (SQLException e) {
            this.logErrorException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }

    private Integer getConversationId(Conversation conversation) throws SQLException {
        try {
            this.getConnection();
            String query = "SELECT * FROM conversations WHERE admin = ? AND creationDate = ? AND type = ?";

            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, conversation.getAdmin().getId());
            this.preparedStatement.setString(2, conversation.getCreationDate());
            this.preparedStatement.setString(3, conversation.getTypeString());

            ResultSet resultSet = this.preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            this.logErrorException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }
}

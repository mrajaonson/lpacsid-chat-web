package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.Conversation;
import fr.lpacsid.chat.beans.Participation;
import fr.lpacsid.chat.beans.User;
import fr.lpacsid.chat.enums.ConversationTypes;
import fr.lpacsid.chat.utils.LoggerUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ConversationDaoImpl implements ConversationDao {

    private final DaoFactory daoFactory;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private final ParticipationDao participationDao;
    private final UserDao userDao;
    private final MessageDao messageDao;

    public ConversationDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
        this.participationDao = this.daoFactory.getParticipationDao();
        this.userDao = this.daoFactory.getUserDao();
        this.messageDao = this.daoFactory.getMessageDao();
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
    public void createConversation(Conversation conversation) throws SQLException {
        try {
            Boolean exists = this.checkIfConversationExists(conversation);
            this.getConnection();

            if (!exists) {
                String query = "INSERT INTO conversations(prime, creationDate, label, type) VALUES(?, ?, ?, ?)";
                this.preparedStatement = this.connection.prepareStatement(query);

                this.preparedStatement.setInt(1, conversation.getPrime().getId());
                this.preparedStatement.setString(2, conversation.getCreationDate());
                this.preparedStatement.setString(3, conversation.getLabel());
                this.preparedStatement.setString(4, conversation.getTypeString());

                int rowAffected = this.preparedStatement.executeUpdate();
                LoggerUtility.logInsertQuery("CONVERSATIONS", rowAffected);

                // Create participations
                Integer conversationId = this.getConversationId(conversation);
                for (Participation participation : conversation.getParticipations()) {
                    participation.setConversation(conversationId);
                    this.participationDao.createParticipation(participation);
                }
            }
        } catch (SQLException e) {
            LoggerUtility.logException(e);
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
                Integer primeId = resultSet.getInt("prime");
                String creationDate = resultSet.getString("creationDate");
                String label = resultSet.getString("label");
                String type = resultSet.getString("type");

                User prime = this.userDao.readUserById(primeId);
                List<Participation> participations = this.participationDao.readAllConversationParticipations(id);

                return new Conversation(id, prime, creationDate, participations, label, type);
            }
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }

    @Override
    public void updateConversation(Conversation conversation) throws SQLException {
        try {
            this.getConnection();
            String query = "UPDATE conversations SET label = ? WHERE id = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setString(1, conversation.getLabel());
            this.preparedStatement.setInt(2, conversation.getId());

            int rowAffected = this.preparedStatement.executeUpdate();
            LoggerUtility.logUpdateQuery(  "conversations", rowAffected, conversation.getId());
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public void deleteConversation(Integer id) throws SQLException {
        try {
            this.getConnection();
            String query = "DELETE FROM conversations WHERE id = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, id);

            int rowAffected = this.preparedStatement.executeUpdate();
            LoggerUtility.logDeleteQuery("conversations", rowAffected, id);

            this.messageDao.deleteMessagesByConversationId(id);
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public List<Conversation> readAllUserConversations(Integer userId) throws SQLException {
        try {
            this.getConnection();
            List<Conversation> conversations = new ArrayList<>();
            List<Integer> conversationsIdSender = this.readAllUserConversationsId(userId);

            for (Integer conversationId : conversationsIdSender) {
                Conversation conversation = this.readConversation(conversationId);
                if (conversation != null) {
                    conversation.setDiscussionLabel(userId);
                    conversations.add(conversation);
                }
            }
            return conversations;
        } catch (SQLException e) {
            LoggerUtility.logException(e);
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

            String query = "SELECT * FROM participations WHERE user = ? ";
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
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }

    @Override
    public Boolean checkIfConversationExists(Conversation conversation) throws SQLException {
        if (!conversation.getType().equals(ConversationTypes.DISCUSSION)) {
            return false;
        }
        try {
            this.getConnection();
            String query = "SELECT 1 AS result FROM conversations WHERE id IN (SELECT conversation FROM participations WHERE user IN (?, ?) GROUP BY conversation HAVING COUNT(DISTINCT user) >= 2) AND type = 'DISCUSSION' LIMIT 1;";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, conversation.getParticipations().get(0).getUser().getId());
            this.preparedStatement.setInt(2, conversation.getParticipations().get(1).getUser().getId());

            ResultSet resultSet = this.preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
        return false;
    }

    private Integer getConversationId(Conversation conversation) throws SQLException {
        try {
            this.getConnection();
            String query = "SELECT * FROM conversations WHERE prime = ? AND creationDate = ? AND type = ?";

            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, conversation.getPrime().getId());
            this.preparedStatement.setString(2, conversation.getCreationDate());
            this.preparedStatement.setString(3, conversation.getTypeString());

            ResultSet resultSet = this.preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }
}

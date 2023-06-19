package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.Participant;
import fr.lpacsid.chat.beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticipantDaoImpl implements ParticipantDao {

    private final DaoFactory daoFactory;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private final UserDao userDao;

    public ParticipantDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
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
    public void createParticipant(Participant participant) throws SQLException {
        try {
            this.getConnection();

            String query = "INSERT INTO participants(conversation, user, addDate) VALUES (?, ?, ?)";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, participant.getConversation());
            this.preparedStatement.setInt(2, participant.getUser().getId());
            this.preparedStatement.setString(3, participant.getAddDate());

            this.preparedStatement.executeUpdate();
        } catch (SQLException e) {
            this.logErrorException(e);
        } finally {
            this.closeConnection();
        }
    }

    @Override
    public Participant readParticipant(Integer conversationId, Integer userId) throws SQLException {
        try {
            this.getConnection();

            String query = "SELECT * FROM participants WHERE conversation = ? AND user = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, conversationId);
            this.preparedStatement.setInt(2, userId);

            ResultSet resultSet = this.preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String addDate = resultSet.getString("addDate");

                User user = this.userDao.readUserById(userId);

                return new Participant(id, conversationId, user, addDate);
            }
        } catch (SQLException e) {
            this.logErrorException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }

    @Override
    public void updateParticipant(Participant participant) throws SQLException {

    }

    @Override
    public void deleteParticipant(Integer id) throws SQLException {

    }

    @Override
    public List<Participant> readAllConversationParticipants(Integer conversationId) throws SQLException {
        try {
            this.getConnection();
            List<Participant> participants = new ArrayList<>();

            String query = "SELECT * FROM participants WHERE conversation = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, conversationId);

            ResultSet resultSet = this.preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String addDate = resultSet.getString("addDate");
                Integer userId = resultSet.getInt("user");

                User user = this.userDao.readUserById(userId);
                Participant participant = new Participant(id, conversationId, user, addDate);
                participants.add(participant);
            }
            return participants;
        } catch (SQLException e) {
            this.logErrorException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }
}

package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.Participation;
import fr.lpacsid.chat.beans.User;
import fr.lpacsid.chat.utils.LoggerUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ParticipationDaoImpl implements ParticipationDao {

    private final DaoFactory daoFactory;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private final UserDao userDao;

    public ParticipationDaoImpl(DaoFactory daoFactory) {
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

    @Override
    public Integer createParticipation(Participation participation) throws SQLException {
        Integer insertedId = null;
        try {
            this.getConnection();

            String query = "INSERT INTO participations(conversation, user, addDate, role) VALUES (?, ?, ?, ?)";
            this.preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            this.preparedStatement.setInt(1, participation.getConversation());
            this.preparedStatement.setInt(2, participation.getUser().getId());
            this.preparedStatement.setString(3, participation.getAddDate());
            this.preparedStatement.setString(4, participation.getStringRole());

            this.preparedStatement.executeUpdate();
            ResultSet generatedKeys = this.preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                insertedId = generatedKeys.getInt(1);
                LoggerUtility.logInsertQuery("PARTICIPATIONS", insertedId);
            }

            return insertedId;
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
        return insertedId;
    }

    @Override
    public Participation readParticipation(Integer conversationId, Integer userId) throws SQLException {
        try {
            this.getConnection();

            String query = "SELECT * FROM participations WHERE conversation = ? AND user = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, conversationId);
            this.preparedStatement.setInt(2, userId);

            ResultSet resultSet = this.preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String addDate = resultSet.getString("addDate");
                String role = resultSet.getString("role");

                User user = this.userDao.readUserById(userId);

                return new Participation(id, conversationId, user, addDate, role);
            }
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }

    @Override
    public void updateParticipation(Participation participation) throws SQLException {

    }

    @Override
    public void deleteParticipation(Integer id) throws SQLException {

    }

    @Override
    public List<Participation> readAllConversationParticipations(Integer conversationId) throws SQLException {
        try {
            this.getConnection();
            List<Participation> participations = new ArrayList<>();

            String query = "SELECT * FROM participations WHERE conversation = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, conversationId);

            ResultSet resultSet = this.preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String addDate = resultSet.getString("addDate");
                Integer userId = resultSet.getInt("user");
                String role = resultSet.getString("role");

                User user = this.userDao.readUserById(userId);
                Participation participation = new Participation(id, conversationId, user, addDate, role);
                participations.add(participation);
            }
            return participations;
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }
}

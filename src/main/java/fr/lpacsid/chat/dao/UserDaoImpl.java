package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoImpl implements UserDao {

    private final DaoFactory daoFactory;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public UserDaoImpl(DaoFactory daoFactory) {
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
    public void createUser(User user) throws SQLException {
        try {
            getConnection();
            String query = "INSERT INTO users(login, password) VALUES(?, ?)";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setString(1, user.getLogin());
            this.preparedStatement.setString(2, user.getPassword());

            this.preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public User readUser(String username) throws SQLException {
        try {
            getConnection();
            String query = "SELECT * FROM users WHERE login = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setString(1, username);

            ResultSet resultSet = this.preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String creationDate = resultSet.getString("creationDate");
                String status = resultSet.getString("status");
                String lastConnection = resultSet.getString("lastConnection");
                return new User(id, username, creationDate, status, lastConnection);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return null;
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public boolean validateUser(String username, String password) throws SQLException {
        try {
            getConnection();
            String query = "SELECT * FROM users WHERE login = ? AND password = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setString(1, username);
            this.preparedStatement.setString(2, password);

            ResultSet resultSet = this.preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return false;
    }

    public User readUserById(Integer id) throws SQLException {
        try {
            this.getConnection();
            String query = "SELECT * FROM users WHERE id = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, id);

            ResultSet resultSet = this.preparedStatement.executeQuery();
            if (resultSet.next()) {
                String login = resultSet.getString("login");
                String creationDate = resultSet.getString("creationDate");
                String status = resultSet.getString("status");
                String lastConnection = resultSet.getString("lastConnection");
                return new User(id, login, creationDate, status, lastConnection);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return null;
    }
}

package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.User;
import fr.lpacsid.chat.utils.LoggerUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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
            String query = "INSERT INTO users(username, password, creationDate, status, lastConnection) VALUES(?, ?, ?, ?,?)";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setString(1, user.getUsername());
            this.preparedStatement.setString(2, user.getPassword());
            this.preparedStatement.setString(3, user.getCreationDate());
            this.preparedStatement.setString(4, user.getStatusString());
            this.preparedStatement.setString(5, user.getLastConnection());

            int rowAffected = this.preparedStatement.executeUpdate();

            if (rowAffected > 0) {
                System.out.println("Users : insert " + rowAffected + " rows.");
            } else {
                System.out.println("Users : No rows inserted.");
            }
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public User readUser(String username) throws SQLException {
        try {
            getConnection();
            String query = "SELECT id, creationDate, status, lastConnection FROM users WHERE username = ?";
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
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        try {
            getConnection();
            String query = "UPDATE users SET username = ?, status = ?, lastConnection = ? WHERE id = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setString(1, user.getUsername());
            this.preparedStatement.setString(2, user.getStatusString());
            this.preparedStatement.setString(3, user.getLastConnection());
            this.preparedStatement.setInt(4, user.getId());

            int rowAffected = this.preparedStatement.executeUpdate();

            if (rowAffected > 0) {
                System.out.println("Row with ID " + user.getId() + " updated successfully.");
            } else {
                System.out.println("No rows updated. Row with ID " + user.getId() + " not found.");
            }
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public boolean validateUser(String username, String password) throws SQLException {
        try {
            getConnection();
            String query = "SELECT 1 FROM users WHERE username = ? AND password = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setString(1, username);
            this.preparedStatement.setString(2, password);

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

    @Override
    public User readUserById(Integer id) throws SQLException {
        try {
            this.getConnection();
            String query = "SELECT id, username, creationDate, status, lastConnection FROM users WHERE id = ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, id);

            ResultSet resultSet = this.preparedStatement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String creationDate = resultSet.getString("creationDate");
                String status = resultSet.getString("status");
                String lastConnection = resultSet.getString("lastConnection");
                return new User(id, username, creationDate, status, lastConnection);
            }
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }

    @Override
    public List<User> readAllUsers(Integer userId) throws SQLException {
        try {
            this.getConnection();
            List<User> users = new ArrayList<>();

            String query = "SELECT id, username, creationDate, status, lastConnection FROM users WHERE id != ?";
            this.preparedStatement = this.connection.prepareStatement(query);

            this.preparedStatement.setInt(1, userId);

            ResultSet resultSet = this.preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String creationDate = resultSet.getString("creationDate");
                String status = resultSet.getString("status");
                String lastConnection = resultSet.getString("lastConnection");

                User user = new User(id, username, creationDate, status, lastConnection);
                users.add(user);
            }

            return users;
        } catch (SQLException e) {
            LoggerUtility.logException(e);
        } finally {
            this.closeConnection();
        }
        return null;
    }
}

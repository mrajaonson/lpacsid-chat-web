package fr.lpacsid.chat.db;

import fr.lpacsid.chat.UserStatus;
import fr.lpacsid.chat.beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDB {
    public void createUser(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            ConnectDB connectDB = new ConnectDB();
            connection = connectDB.getConnection();
            String query = "INSERT INTO users(login, password) VALUES(?, ?)";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public User readUser(String login) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            ConnectDB connectDB = new ConnectDB();
            connection = connectDB.getConnection();
            String query = "SELECT * FROM users WHERE login = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, login);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String creationDate = resultSet.getString("creationDate");
                String status = resultSet.getString("status");
                String lastConnection = resultSet.getString("lastConnection");
                return new User(id, login, creationDate, status, lastConnection);
            }
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

    public User readUserById(Integer id) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            ConnectDB connectDB = new ConnectDB();
            connection = connectDB.getConnection();
            String query = "SELECT * FROM users WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String login = resultSet.getString("login");
                String creationDate = resultSet.getString("creationDate");
                String status = resultSet.getString("status");
                String lastConnection = resultSet.getString("lastConnection");
                return new User(id, login, creationDate, status, lastConnection);
            }
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

    public boolean validateUser(String login, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            ConnectDB connectDB = new ConnectDB();
            connection = connectDB.getConnection();
            String query = "SELECT * FROM users WHERE login = ? AND password = ?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
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
        return false;
    }
}

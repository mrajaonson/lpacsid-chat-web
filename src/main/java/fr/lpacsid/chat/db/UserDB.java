package fr.lpacsid.chat.db;

import fr.lpacsid.chat.beans.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDB {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private String query;

    private void getConnection() throws SQLException {
        ConnectDB connectDB = new ConnectDB();
        this.connection = connectDB.getConnection();
    }

    private void closeConnection() throws SQLException {
        this.preparedStatement.close();
        this.connection.close();
    }

    public UserDB() {}

    public void createUser(User user) throws SQLException {
        try {
            this.getConnection();
            this.query = "INSERT INTO users(login, password) VALUES(?, ?)";
            this.preparedStatement = this.connection.prepareStatement(this.query);

            this.preparedStatement.setString(1, user.getLogin());
            this.preparedStatement.setString(2, user.getPassword());

            this.preparedStatement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
    }

    public User readUser(String login) throws SQLException {
        try {
            this.getConnection();
            this.query = "SELECT * FROM users WHERE login = ?";
            this.preparedStatement = this.connection.prepareStatement(this.query);

            this.preparedStatement.setString(1, login);

            ResultSet resultSet = this.preparedStatement.executeQuery();
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
            this.closeConnection();
        }
        return null;
    }

    public User readUserById(Integer id) throws SQLException {
        try {
            this.getConnection();
            this.query = "SELECT * FROM users WHERE id = ?";
            this.preparedStatement = this.connection.prepareStatement(this.query);

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
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return null;
    }

    public boolean validateUser(String login, String password) throws SQLException {
        try {
            this.getConnection();
            this.query = "SELECT * FROM users WHERE login = ? AND password = ?";
            this.preparedStatement = this.connection.prepareStatement(this.query);

            this.preparedStatement.setString(1, login);
            this.preparedStatement.setString(2, password);

            ResultSet resultSet = this.preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDB.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            this.closeConnection();
        }
        return false;
    }
}

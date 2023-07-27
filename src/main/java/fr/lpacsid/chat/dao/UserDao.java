package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    void createUser(User user) throws SQLException;

    User readUser(String username) throws SQLException;

    User readUserById(Integer id) throws SQLException;

    void updateUser(User user);

    void deleteUser(String username);

    boolean validateUser(String username, String password) throws SQLException;

    List<User> readAllUsers(Integer userId) throws SQLException;
}

package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.Conversation;

import java.sql.SQLException;
import java.util.List;

public interface ConversationDao {
    void createConversation(Conversation conversation) throws SQLException;

    void readConversation(Integer id) throws SQLException;

    void updateConversation(Integer id) throws SQLException;

    void deleteConversation(Integer id) throws SQLException;

    List<Conversation> readAllUserConversations(String username) throws SQLException;
}

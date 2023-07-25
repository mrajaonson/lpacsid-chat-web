package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.Conversation;

import java.sql.SQLException;
import java.util.List;

public interface ConversationDao {
    void createConversation(Conversation conversation) throws SQLException;

    Conversation readConversation(Integer id) throws SQLException;

    void updateConversation(Integer id) throws SQLException;

    void deleteConversation(Integer id) throws SQLException;

    List<Conversation> readAllUserConversations(Integer userId) throws SQLException;

    List<Integer> readAllUserConversationsId(Integer userId) throws SQLException;

    Boolean checkIfConversationExists(Conversation conversation) throws SQLException;
}

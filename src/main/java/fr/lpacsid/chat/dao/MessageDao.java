package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.Message;

import java.sql.SQLException;
import java.util.List;

public interface MessageDao {
    Integer createMessage(Message message) throws SQLException;

    Message readMessage(Integer id) throws SQLException;

    void updateMessage(Message message) throws SQLException;

    void deleteMessage(Integer id) throws SQLException;

    void deleteMessagesByConversationId(Integer id) throws SQLException;

    List<Message> readAllConversationMessages(Integer conversation) throws SQLException;
}

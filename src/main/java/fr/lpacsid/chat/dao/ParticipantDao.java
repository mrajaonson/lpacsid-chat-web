package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.Participant;

import java.sql.SQLException;
import java.util.List;

public interface ParticipantDao {
    void createParticipant(Participant participant) throws SQLException;

    Participant readParticipant(Integer conversation, Integer user) throws SQLException;

    void updateParticipant(Participant participant) throws SQLException;

    void deleteParticipant(Integer id) throws SQLException;

    List<Participant> readAllConversationParticipants(Integer conversationId) throws SQLException;
}

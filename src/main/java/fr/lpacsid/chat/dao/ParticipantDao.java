package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.Participation;

import java.sql.SQLException;
import java.util.List;

public interface ParticipantDao {
    void createParticipant(Participation participant) throws SQLException;

    Participation readParticipant(Integer conversation, Integer user) throws SQLException;

    void updateParticipant(Participation participant) throws SQLException;

    void deleteParticipant(Integer id) throws SQLException;

    List<Participation> readAllConversationParticipants(Integer conversationId) throws SQLException;
}

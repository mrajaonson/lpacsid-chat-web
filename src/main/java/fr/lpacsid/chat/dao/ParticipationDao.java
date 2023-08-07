package fr.lpacsid.chat.dao;

import fr.lpacsid.chat.beans.Participation;

import java.sql.SQLException;
import java.util.List;

public interface ParticipationDao {
    Integer createParticipation(Participation participation) throws SQLException;

    Participation readParticipation(Integer conversation, Integer user) throws SQLException;

    void updateParticipation(Participation participation) throws SQLException;

    void deleteParticipation(Integer id) throws SQLException;

    List<Participation> readAllConversationParticipations(Integer conversationId) throws SQLException;
}

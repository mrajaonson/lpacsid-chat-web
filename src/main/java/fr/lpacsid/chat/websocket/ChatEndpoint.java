package fr.lpacsid.chat.websocket;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import fr.lpacsid.chat.beans.Conversation;
import fr.lpacsid.chat.beans.User;
import fr.lpacsid.chat.beans.Message;
import fr.lpacsid.chat.beans.WebsocketMessage;
import fr.lpacsid.chat.dao.ConversationDao;
import fr.lpacsid.chat.dao.DaoFactory;
import fr.lpacsid.chat.dao.MessageDao;
import fr.lpacsid.chat.dao.UserDao;
import fr.lpacsid.chat.enums.ConversationTypes;
import fr.lpacsid.chat.utils.LoggerUtility;

import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat/{username}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ChatEndpoint {
    private Session session;
    private static final Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
    private static final HashMap<String, User> users = new HashMap<>();

    private final UserDao userDao;
    private final MessageDao messageDao;
    private final ConversationDao conversationDao;

    public ChatEndpoint() {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.userDao = daoFactory.getUserDao();
        this.messageDao = daoFactory.getMessageDao();
        this.conversationDao = daoFactory.getConversationDao();
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException, SQLException {
        this.session = session;
        chatEndpoints.add(this);

        User user = this.userDao.readUser(username);
        users.put(session.getId(), user);
    }

    @OnMessage
    public void onMessage(Session session, WebsocketMessage websocketMessage) throws IOException, EncodeException, SQLException {
        try {
            websocketMessage.setSender(users.get(session.getId()));
            websocketMessage.initDate();

            if (websocketMessage.getType().equals("MESSAGE")) {
                // Persistence en base du message
                Message message = new Message(websocketMessage.getConversation(), websocketMessage.getSender(), websocketMessage.getContent());
                this.messageDao.createMessage(message);
            } else {
                // Création de la conversation
                Conversation conversation = new Conversation(websocketMessage.getSender(), websocketMessage.getConversationTypesFromTypeString());
                // Ajout des participants
                for (String participant : websocketMessage.getParticipations()) {
                    User userParticipant = userDao.readUserById(Integer.valueOf(participant));
                    conversation.addParticipant(userParticipant);
                }
                // Persistence en base
                Integer conversationId = this.conversationDao.createConversation(conversation);
                conversation.setId(conversationId);
                conversation.setConversationLabel(websocketMessage.getSender().getId());
                // Mise à jour du websocket message
                websocketMessage.setConversation(conversationId);
                websocketMessage.setConversationLabel(conversation.getLabel());
            }
            broadcast(websocketMessage);
        } catch (Exception e) {
            LoggerUtility.logException(e);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        chatEndpoints.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(WebsocketMessage websocketMessage) throws IOException, EncodeException {
        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().sendObject(websocketMessage);
                } catch (IOException | EncodeException e) {
                    LoggerUtility.logException(e);
                }
            }
        });
    }
}

package fr.lpacsid.chat.websocket;

import com.google.gson.Gson;
import fr.lpacsid.chat.beans.WebsocketMessage;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;


public class MessageEncoder implements Encoder.Text<WebsocketMessage> {

    private static final Gson gson = new Gson();

    @Override
    public String encode(WebsocketMessage websocketMessage) throws EncodeException {
        return gson.toJson(websocketMessage);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}

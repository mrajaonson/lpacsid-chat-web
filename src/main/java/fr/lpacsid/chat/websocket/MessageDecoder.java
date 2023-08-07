package fr.lpacsid.chat.websocket;

import fr.lpacsid.chat.beans.WebsocketMessage;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

import com.google.gson.Gson;

public class MessageDecoder implements Decoder.Text<WebsocketMessage> {

    private static final Gson gson = new Gson();

    @Override
    public WebsocketMessage decode(String s) throws DecodeException {
        return gson.fromJson(s, WebsocketMessage.class);
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
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

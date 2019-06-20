package victor.training.springwebsockets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ChatSocket extends TextWebSocketHandler {

    static private Map<String, List<WebSocketSession>> openSessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String roomId = getRoomId(session);
        log.debug("Connected to room: "+roomId);
        openSessions.computeIfAbsent(roomId, r -> new ArrayList<>()).add(session);
    }

    private String getRoomId(WebSocketSession session) {
        String[] arr = session.getUri().toString().split("/");
        return arr[arr.length - 1];
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String roomId = getRoomId(session);
        log.debug("Sent message to room: "+roomId + " -> " + message.getPayload());
        for (WebSocketSession socketSession : openSessions.get(roomId)) {
            socketSession.sendMessage(message);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String roomId = getRoomId(session);
        log.debug("Disconnected from room: "+roomId);
        openSessions.get(roomId).remove(session);
    }


}

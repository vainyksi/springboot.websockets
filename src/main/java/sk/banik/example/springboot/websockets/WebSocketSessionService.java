package sk.banik.example.springboot.websockets;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class WebSocketSessionService {

    private static Logger LOG = LoggerFactory.getLogger(WebSocketSessionService.class);

    private final Map<String, WebSocketSession> wsSessions = new ConcurrentHashMap<>();

    public void registerSession(WebSocketSession session) {
        LOG.info("registerSession, session: {}", session.getId());
        wsSessions.put(session.getId(), session);
    }

    public void unregisterSession(final String wsSessionId) {
        unregisterSession(wsSessionId, false);
    }

    private void unregisterSession(final String wsSessionId, final boolean close) {
        LOG.info("unregisterSession, session: {}", wsSessionId);
        WebSocketSession wsSession = wsSessions.remove(wsSessionId);
        if (wsSession != null && close) {
            try {
                wsSession.close();
            } catch (final IOException e) {
                LOG.error("WS session: [{}] cannot be unregistered!!!", wsSession, e);
            }
        }
    }

    public void publishToAll(final String message) {
        LOG.debug("Publishing message to websocket clients, count {}...", wsSessions.size());
        wsSessions.values().forEach(session -> {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (final IOException e) {
                LOG.error("Unable to publish to all websocket clients", e);
            }
        });
    }
}

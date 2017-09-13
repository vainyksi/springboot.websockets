package sk.banik.example.springboot.websockets.config;

import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import sk.banik.example.springboot.websockets.WebSocketSessionService;

@Component
public class WebSocketHandlerImpl implements WebSocketHandler {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketHandlerImpl.class);

    private WebSocketSessionService webSocketSessionService;

    @Override
    public void afterConnectionEstablished(@Nonnull final WebSocketSession session) throws Exception {
        LOG.info("afterConnectionEstablished ");
        webSocketSessionService.registerSession(session);
    }

    @Override
    public void handleMessage(@Nonnull final WebSocketSession session,
                              @Nonnull final WebSocketMessage<?> message) throws Exception {
        LOG.info("handleMessage, session: {}, message {}", session, message);
    }

    @Override
    public void handleTransportError(@Nonnull final WebSocketSession session,
                                     @Nonnull final Throwable exception) throws Exception {
        LOG.info("handleTransportError: {}", exception);
        webSocketSessionService.unregisterSession(session.getId());
    }

    @Override
    public void afterConnectionClosed(@Nonnull final WebSocketSession session,
                                      @Nonnull final CloseStatus closeStatus) throws Exception {
        LOG.info("afterConnectionClosed, session: {}, close status: {}", session, closeStatus);
        webSocketSessionService.unregisterSession(session.getId());
    }

    @Autowired
    public void setWebSocketSessionService(@Nonnull final WebSocketSessionService webSocketSessionService) {
        this.webSocketSessionService = webSocketSessionService;
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

}

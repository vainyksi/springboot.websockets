package sk.banik.example.springboot.websockets.config;

import java.util.Map;
import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class WsHandshakeInterceptor implements HandshakeInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(WsHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(@Nonnull final ServerHttpRequest request,
                                   @Nonnull final ServerHttpResponse response,
                                   @Nonnull final WebSocketHandler wsHandler,
                                   @Nonnull final Map<String, Object> attributes) throws Exception {
        LOG.info("beforeHandshake");
        return true;
    }

    @Override
    public void afterHandshake(@Nonnull final ServerHttpRequest request,
                               @Nonnull final ServerHttpResponse response,
                               @Nonnull final WebSocketHandler wsHandler,
                               @Nullable final Exception exception) {
        LOG.info("afterHandshake");
    }
}

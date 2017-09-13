package sk.banik.example.springboot.websockets.config;

import javax.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import sk.banik.example.springboot.websockets.util.URLs;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void registerWebSocketHandlers(@Nonnull final WebSocketHandlerRegistry registry) {
        LOG.info("registerWebSocketHandlers");
        registry.addHandler(getWebSocketHandler(), URLs.WS_ENDPOINT_PATH)
                .addInterceptors(new WsHandshakeInterceptor())
                .setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler getWebSocketHandler() {
        return new WebSocketHandlerImpl();
    }

}

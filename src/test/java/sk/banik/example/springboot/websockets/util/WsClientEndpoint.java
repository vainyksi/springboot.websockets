package sk.banik.example.springboot.websockets.util;

import java.util.concurrent.CountDownLatch;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.OnClose;
import javax.websocket.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WsClientEndpoint extends Endpoint{

    private static final Logger LOG = LoggerFactory.getLogger(WsClientEndpoint.class);
    private final CountDownLatch countDownLatch;

    public WsClientEndpoint(final CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void onOpen(final Session session, final EndpointConfig config) {
        LOG.info("WsClientEndpoint connected, sessionId:{}", session.getId());

        session.addMessageHandler(new MessageHandler.Whole<String>() {
            // do not use lambda here - there is type recognition of message handler, which does
            // not work with lambda here
            @Override
            public void onMessage(final String message) {
                LOG.info("WsClientEndpoint received message: {}", message);
                countDownLatch.countDown();
            }
        });
    }

    @OnClose
    public void onClose(final Session session, final CloseReason closeReason) {
        LOG.info("WsClientEndpoint closing, sessionId {}, reason: {}",
            session.getId(), closeReason);
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

}


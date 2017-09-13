package sk.banik.example.springboot.websockets;

import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sk.banik.example.springboot.websockets.util.URLs;
import sk.banik.example.springboot.websockets.util.WsClientEndpoint;

public class WsClientTest {

    private static final Logger LOG = LoggerFactory.getLogger(WsClientTest.class);

    @Test
    public void basicClientCommunicationTest() throws Exception {
        final int messagesCount = 3;
        final CountDownLatch responseCountDown = new CountDownLatch(messagesCount);

        final ClientManager wsClientManager = ClientManager.createClient();
        final WsClientEndpoint clientEndpoint = new WsClientEndpoint(responseCountDown);

        final Session session = wsClientManager.connectToServer(
            clientEndpoint,
            new URI(URLs.WS_ENDPOINT_URL)
        );
        Stream.of(1, 2, 3).forEach(messageNumber -> {
            final String messageText = "ahoj " + messageNumber;
            try {
                session.getBasicRemote().sendText(messageText);
                LOG.info("message sent: [{}]", messageText);
            } catch (IOException e) {
                LOG.error("could not sent message from client, message: {}", messageText);
            }
        });

        responseCountDown.await(5, TimeUnit.SECONDS);

        Assert.assertThat(responseCountDown.getCount(), is(0L));

    }
}

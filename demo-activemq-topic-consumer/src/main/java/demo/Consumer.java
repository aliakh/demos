package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    private final AtomicInteger i = new AtomicInteger();

    @JmsListener(destination = "${application.topic.name}")
    public void onReceive(Message message) {
        LOGGER.info("Received message: " + message);

        Object payload = message.getPayload();
        MessageHeaders headers = message.getHeaders();
        LOGGER.info("Message payload: " + payload);
        LOGGER.info("Message headers: " + headers);

        if (payload instanceof byte[]) {
            LOGGER.info("Message payload as byte array: " + Arrays.toString((byte[]) payload));
            LOGGER.info("Message payload as text: " + new String((byte[]) payload));
        }

        LOGGER.info("Total messages received: " + i.incrementAndGet());
    }
}
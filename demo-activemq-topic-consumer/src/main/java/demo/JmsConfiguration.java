package demo;

import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
public class JmsConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsConfiguration.class);

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${application.topic.name}")
    private String topicName;

    @Bean
    public ActiveMQTopic getTopic() {
        LOGGER.info("Connection to the ActiveMQ by " + brokerUrl + " to the topic " + topicName);
        return new ActiveMQTopic(topicName);
    }
}

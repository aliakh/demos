# demo-activemq-topic-consumer

1. Install Java 7+

2. Select an ActiveMQ to connect 

3. Connect to an ActiveMQ topic by default URL

*gradlew clean build bootRun -Dapplication.topic.name=other.topic*

4. Connect to an ActiveMQ topic by non-default URL

*gradlew clean build bootRun -Dapplication.topic.name=other.topic -Dspring.activemq.broker-url=tcp://localhost:61616*

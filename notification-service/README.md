## Notification Service

### Overview

The Notification Service is responsible for handling online notifications in real-time using WebSocket (SockJS). It communicates with clients to provide updates on game events and player actions.

### Kafka Integration

The service utilizes Kafka for asynchronous messaging to efficiently distribute notifications among users from microservice. Topics in Kafka are used to publish and subscribe to relevant events.

### WebSocket (SockJS)

The WebSocket (SockJS) integration enables bidirectional communication between the server and clients. This allows real-time updates to be pushed to connected clients.

### Usage

Connect to the WebSocket endpoint (`/websocket`) to receive real-time notifications. Subscribe to Kafka topics related to game events to receive updates from the Notification Service for each separate user.

### Configuration

Include the following Kafka and WebSocket configurations in your microservice's `application.yaml`:

```yaml
spring:
  kafka:
    bootstrap-servers: kafka:9092
    properties:
      spring.json.add.type.headers: false
    consumer:
      key-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      group-id: NotificationProcessingGroup
      properties:
        spring.json.trusted.packages: com.bol.interview.common.*
```

### Docker

Include the Notification Service in your `docker-compose.yml`:

```yaml
  notification-service:
    build:
      context: .
      dockerfile: Dockerfile.notification-service  # File containing the Notification Service Docker configuration
    image: mancala-game/notification-service:dev.1.0
    container_name: notification-service.v.1.0
    ports:
      - "8082:8082"  # Expose Notification Service port
    depends_on:
      - eureka
      - kafka
    environment:
      SERVER_PORT: 8082
      EUREKA_CLIENT_SERVICE-URL_DEFAULTZONE: http://eureka:8761/eureka
      SPRING_KAFKA_BOOTSTRAP-SERVERS: kafka:9092
      KEYCLOAK_BASE_URL: http://keycloak:8080
```

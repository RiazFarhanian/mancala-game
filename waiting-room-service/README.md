## Waiting Room Service

### Overview

The Waiting Room Service manages the online matching of players before starting a game. It coordinates the pairing of players.

### Kafka Integration

Kafka is used for handling asynchronous messaging between microservices. The Waiting Room Service publishes Player to relevant topics to send and start game.

### Usage

Make API calls to the Waiting Room Service to join the waiting room, and get matched with opponents.

### Configuration

Include the following Kafka configuration in your microservice's `application.yaml`:

```yaml
# Kafka Configuration
kafka:
  bootstrap-servers: kafka:9092
  properties:
    spring.json.add.type.headers: false
```

### Docker

Include the Waiting Room Service in your `docker-compose.yml`:

```yaml
  #waiting-room-service
  waiting-room-service:
    build:
      context: .
      dockerfile: Dockerfile.waiting-room-service  # Directory containing the waiting-room Service Dockerfile
    image: mancala-game/waiting-room-service:dev.1.0
    container_name: waiting-room-service.v.1.0
    ports:
      - "8083:8083"  # Expose Waiting Room Service port
    depends_on:
      - eureka
      - kafka
    environment:
      SERVER_PORT: 8083
      EUREKA_CLIENT_SERVICE-URL_DEFAULTZONE: http://eureka:8761/eureka
      KEYCLOAK_BASE_URL: http://keycloak:8080
      SPRING_KAFKA_BOOTSTRAP-SERVERS: kafka:9092
```

---

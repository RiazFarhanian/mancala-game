## Mancala Service

### Overview

The Mancala Service contains the main logic of the Mancala game. It handles game rules, player moves, and game state management.

### Usage

Interact with the Mancala Service by making API calls to initiate games, make moves, and retrieve game information.

### API Endpoints

- `/startGame`: Start a new Mancala game.
- `/makeMove/{gameId}/{playerId}/{pitIndex}`: Make a move in the Mancala game.

### Docker

Include the Mancala Service in your `docker-compose.yml`:

```yaml
  mancala-service:
    build:
      context: .
      dockerfile: Dockerfile.mancala-service  # Directory containing the Mancala Service Dockerfile
    image: mancala-game/mancala-service:dev.1.0
    container_name: mancala-service.v.1.0
    ports:
      - "8081:8081"  # Expose Mancala Service port
    depends_on:
      - eureka
      - mongodb
      - kafka
    environment:
      SERVER_PORT: 8081
      SPRING_KAFKA_BOOTSTRAP-SERVERS: kafka:9092
      KEYCLOAK_BASE_URL: http://keycloak:8080
      EUREKA_CLIENT_SERVICE-URL_DEFAULTZONE: http://eureka:8761/eureka
      MONGO-HOST: mongodb # MongoDB container name
      MONGO-PORT: 27017 # MongoDB port
      MONGO-DB: mancala # MongoDB database name
      MONGO-USER: mancala # MongoDB username
      MONGO-PASSWORD: mancala_password # MongoDB password
      DEBUG_MODE: true # Enable debug mode for checking game final state(the game score that is higher than 10 will finish)
```

---

You can continue this pattern for the other microservices, such as Waiting Room Service, Notification Service, and Angular App. Customize the content based on the specific functionalities and configurations of each microservice.
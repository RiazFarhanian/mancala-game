# Mancala Game Microservices Implementation

## Overview

This project is a Mancala game implementation using a microservices architecture. It is designed to be scalable, modular, and easily deployable using Docker Compose. The game consists of several microservices that handle different aspects of the gameplay, user management, and communication between services.

## Author

**Author:** Riaz Farhanian

## Microservices Overview

1. **Eureka (Service Discovery):**
    - Responsible for service discovery within the microservices architecture.

2. **Spring API Gateway:**
    - Acts as the gateway for all incoming requests, routing them to the appropriate microservices.

3. **Keycloak (User Management and Security):**
    - Manages user authentication and ensures the security of the game.

4. **Zookeeper and Kafka (Asynchronous Messaging):**
    - Enables asynchronous communication between microservices using Apache Kafka and Zookeeper.

5. **Mancala Service:**
    - Contains the main logic of the Mancala game.

6. **Waiting Room Service:**
    - Handles the online matching of players before starting a game.

7. **Notification Service:**
    - Implements online notifications using WebSockets for real-time updates.

8. **Angular App:**
    - Provides a beautiful front-end interface for players using Angular.

## Docker Compose

The project is containerized using Docker Compose for easy deployment. To run the entire application, execute the following command:

```bash
docker-compose up
```
This will bring up all the microservices and dependencies.

## Postman Configuration

To facilitate quick testing and learning about the APIs, a Postman configuration is provided. Import the Postman collection located in the `postman` directory.

## Getting Started

1. Clone the repository:

   ```bash
   git clone git@github.com:RiazFarhanian/mancala-game.git
   ```

2. Navigate to the project directory:

   ```bash
   cd mancala-game
   ```

3. Run the Docker Compose command to start the application.

4. Access the Angular app at [http://localhost:4200](http://localhost:8080) to play the Mancala game.


Certainly! Below is a template for the "Design and Architecture" section of your README:

---

# Design and Architecture

## Overview

The Mancala Game Application is designed with a microservices architecture to ensure scalability, modularity, and maintainability. The architecture comprises various microservices, each responsible for specific functionalities, and follows best practices for distributed systems.

## Microservices Architecture

### 1. Eureka (Service Discovery)

- **Purpose:** Enables dynamic service discovery within the application.
- **Role:** Microservices register with Eureka, allowing them to discover and communicate with each other seamlessly.

### 2. Spring API Gateway

- **Purpose:** Acts as the entry point for all incoming requests.
- **Role:** Routes requests to the appropriate microservices, providing a unified interface for clients. Implements load balancing and other gateway functionalities.

### 3. Keycloak (User Management and Security)

- **Purpose:** Manages user authentication and authorization.
- **Role:** Ensures the security of the application by providing OAuth 2.0 access tokens. Integrates with microservices to authenticate users and secure access to protected resources.

### 4. Zookeeper and Kafka (Asynchronous Messaging)

- **Purpose:** Facilitates asynchronous communication between microservices.
- **Role:** Microservices use Kafka for message publishing and subscribing, and Zookeeper for distributed coordination, ensuring reliable and scalable messaging.

### 5. Mancala Service

- **Purpose:** Contains the main logic of the Mancala game.
- **Role:** Manages game state, player moves, and scoring. Interacts with other microservices for matchmaking and notifications.

### 6. Waiting Room Service

- **Purpose:** Handles online matchmaking for players.
- **Role:** Allows players to join a waiting room and find opponents for Mancala games. Coordinates with the Mancala Service to initiate and manage game sessions.

### 7. Notification Service

- **Purpose:** Provides real-time updates to players during gameplay.
- **Role:** Implements WebSocket communication(SockJS) to notify players about game events, moves, and other relevant updates.

### 8. Angular App

- **Purpose:** Offers a visually appealing front-end interface for players.
- **Role:** Allows players to interact with the Mancala game, view game status, and receive real-time updates through the Notification Service.

## Key Components and Technologies

- **Docker Compose:** Used for containerization, making deployment and scaling of microservices straightforward.
- **Postman:** Configuration provided for quick testing and learning of API interactions.
- **Angular:** Employs Angular for the development of the front-end application.

## Diagram

![Microservices Architecture Diagram](link-to-your-diagram-image)

(Include a diagram depicting the relationships and interactions between microservices)

## Future Considerations

As the Mancala Game Application evolves, we plan to explore further enhancements, such as:

- Integration of additional features like player statistics and leaderboards.
- Continuous optimization of microservices for improved performance.
- Expansion of the front-end application with more interactive elements and user customization.


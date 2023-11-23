## Eureka (Service Discovery)

### Overview

The Eureka service is responsible for facilitating service discovery within the microservices architecture. It enables microservices to register and discover each other dynamically.

### Usage

To integrate with Eureka, each microservice should include the necessary configurations in its properties file. Ensure that the Eureka server is up and running before starting other microservices.

### Configuration

Add the following configuration to your microservice's `application.properties`:

```properties
  eureka:
    build:
      context: ./eureka
    ports:
      - "8761:8761"
```

### Docker

To run Eureka using Docker, include the following in your `docker-compose.yml`:

```yaml
services:
  eureka:
    build:
      context: ./eureka
    ports:
      - "8761:8761"  
```

---

 
## Spring API Gateway

### Overview

The Spring API Gateway is the entry point for all incoming requests. It handles routing, load balancing, and provides a unified interface for clients.

### Usage

Configure your microservices to register with the API Gateway. Update the API Gateway's configuration to include routes for each microservice.

### Configuration

Add route configurations to `application.yml`:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: mancala-service
          uri: lb://mancala-service
          predicates:
            - Path=/mancala/**
```

### Docker

Include the API Gateway service in your `docker-compose.yml`:

```yaml
services:
  api-gateway:
    image: your-api-gateway-image
    ports:
      - "8080:8080"
```

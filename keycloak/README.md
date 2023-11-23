## Keycloak (User Management and Security)

### Overview

Keycloak is used for user management and ensuring the security of the Mancala game. It provides authentication and authorization services, ensuring secure access to the game's resources.

### Usage

Integrate Keycloak with your microservices to enable secure user authentication. Configure your microservices to rely on Keycloak for user-related functionalities.

### Configuration

Include the following Keycloak configuration in your microservice's `application.yaml`:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: keycloak:8080/realms/mancala/protocol/openid-connect/certs}
      client:
        provider:
          keycloak:
            authorization-uri: keycloak:8080/realms/mancala/protocol/openid-connect/auth}
            user-info-uri: keycloak:8080/realms/mancala/protocol/openid-connect/userinfo}
            jwk-set-uri: keycloak:8080/realms/mancala/protocol/openid-connect/certs}
            token-uri: keycloak:8080/realms/mancala/protocol/openid-connect/token}
        registration:
          keycloak:
            client-id: mancala_rest_api
            authorization-grant-type: password
            scope: openid
            redirect-uri: http://localhost:4200
            client-authentication-method: post

```
Note: The reason that the `authorization-uri`, `user-info-uri`, and `jwk-set-uri` are set is because of containerization. The microservices are running in containers, so they are not able to access the Keycloak server using `localhost`. and because of that its required to use the name of the service in the docker-compose file. 

### Docker

Include the Keycloak service in your `docker-compose.yml`:

```yaml
  keycloak:
    image: quay.io/keycloak/keycloak:22.0.5
    environment:
      KEYCLOAK_ADMIN: admin_username
      KEYCLOAK_ADMIN_PASSWORD: admin_password
      KC_DB: postgres
      KC_DB_URL: jdbc:postgresql://postgres/your_db_name
      KC_DB_USERNAME: user  ## This is the username for the database
      KC_DB_PASSWORD: password ## This is the password for the database
      KC_METRICS_ENABLED: false
    volumes:
      - ./keycloak/import-data:/opt/keycloak/data/import
    command: ["start-dev", "--import-realm"]
    ports:
      - "8080:8080"  # Expose Keycloak port
    depends_on:
      - postgres
```

---


package com.bol.interview.notificationservice.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class InboundChannelConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(InboundChannelConfig.class);

    @Value(value = "${notification-service.jwk}")
    private String jwkUri;


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {

            @Override
            public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {

                StompHeaderAccessor accessor =
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (StompCommand.CONNECT.equals(Objects.requireNonNull(accessor).getCommand())) {
                    // Extract the OAuth 2.0 JWT token from the WebSocket handshake

                    try {
                        if (isJwtTokenExist(Objects.requireNonNull(accessor))) {

                            String jwtToken = getJwtToken(accessor);


                            // Set up the JWT decoder with the JWK endpoint
                            JwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkUri).build();

                            // Decode and validate the JWT
                            Jwt jwt = jwtDecoder.decode(jwtToken);

                            // Create authorities for the user (customize as needed)
                            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

                            // Create an authentication token
                            JwtAuthenticationToken user = new JwtAuthenticationToken(jwt, authorities);

                            user.setAuthenticated(true);
                            SecurityContextHolder.getContext().setAuthentication(user);

                            accessor.setUser(user);
                            logger.info(user.getName() + " User authenticated");
                        }
                    } catch (Exception e) {
                        logger.error("Token Verification failed!({})", e.getMessage());
                        throw new RuntimeException("Token Verification Failed!", e);
                    }
                }
                return message;
            }
        });
    }


    private static String getJwtToken(StompHeaderAccessor accessor) {
        String jwtToken;
        var nativeHeaders = accessor.getMessageHeaders().get("nativeHeaders", Map.class);
        assert nativeHeaders != null;
        jwtToken = ((List<?>) nativeHeaders.get("Authorization")).get(0).toString();
        return jwtToken;
    }

    private static boolean isJwtTokenExist(StompHeaderAccessor accessor) {
        return accessor.getMessageHeaders().containsKey("nativeHeaders") &&
                Objects.requireNonNull(accessor.getMessageHeaders().get("nativeHeaders", Map.class))
                        .containsKey("Authorization");
    }
}
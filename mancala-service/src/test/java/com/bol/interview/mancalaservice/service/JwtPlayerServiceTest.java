package com.bol.interview.mancalaservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class JwtPlayerServiceTest {

    @Mock
    private Jwt jwt;

    @Mock
    private SecurityContextHolder securityContextHolderMock;

    @Mock
    private SecurityContext securityContextMock;

    @Mock
    private Authentication authenticationMock;



    PlayerService playerService;



    @BeforeEach
    void setUp() {
        playerService = new JwtPlayerService();
    }

    @Test
    void getCurrentPlayer() {
        // Mocking the jwt
        when(jwt.getSubject()).thenReturn("test");

        // Mocking the SecurityContext
        SecurityContext securityContextMock = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContextMock);

        // Mocking the authentication
        when(securityContextMock.getAuthentication()).thenReturn(authenticationMock);
        when(authenticationMock.getPrincipal()).thenReturn(jwt);

        // Testing the method
        assertEquals("test", playerService.getCurrentPlayer().userName());

        when(authenticationMock.getPrincipal()).thenReturn("");
        assertThrows(ClassCastException.class, () -> playerService.getCurrentPlayer());


    }
}
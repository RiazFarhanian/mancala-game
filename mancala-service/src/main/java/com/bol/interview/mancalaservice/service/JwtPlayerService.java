package com.bol.interview.mancalaservice.service;

import com.bol.interview.common.dto.PlayerDto;
import com.bol.interview.mancalaservice.exception.ExceptionMessages;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class JwtPlayerService implements PlayerService {
    private static final String NAME_CLAIM = "name";

    @Override
    public PlayerDto getCurrentPlayer() throws ClassCastException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the principal (which typically contains the username)
        Object principal = authentication.getPrincipal();

        if (principal instanceof Jwt jwt){
            String username = jwt.getSubject();
            String name = jwt.getClaim(NAME_CLAIM);
            return new PlayerDto(username,name);
        }
        throw new  ClassCastException(ExceptionMessages.JWT_CAST_EXCEPTION);
    }
}

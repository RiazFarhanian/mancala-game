package com.bol.interview.waitingroomservice.service;

import com.bol.interview.common.dto.Player;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    public Player getCurrentPlayer() throws NullPointerException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the principal (which typically contains the username)
        Object principal = authentication.getPrincipal();

        if (principal instanceof Jwt jwt){
            String username = jwt.getClaim("preferred_username");
            String name = jwt.getClaim("name");
            return new Player(username,name);
        }
        throw new NullPointerException("Couldn't retrieve Player Information");
    }
}

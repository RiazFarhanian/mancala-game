package com.bol.interview.waitingroomservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    @RequestMapping("/")
    public String home() {
        String username = "Unknown User";
        // Retrieve the current authentication details from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the principal (which typically contains the username)
        Object principal = authentication.getPrincipal();


        if (principal instanceof String) {
            // Assuming the principal is a String (username)
            username = (String) principal;

        } else if (principal instanceof Jwt jwt){
            username = jwt.getClaim("preferred_username");
            String name = jwt.getClaim("given_name");
            String family = jwt.getClaim("family_name");
            return String.format("Hello %s %s (%s)",name,family,username);
        }
        return "Hello " + username;
    }
}

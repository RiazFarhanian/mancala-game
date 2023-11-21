package com.bol.interview.mancalaservice.configuration;

import com.bol.interview.mancalaservice.service.validation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class Beans {

    @Bean
    public GameValidation moveGameValidation(){
        List<GameValidation> gameValidations = new ArrayList<>();
        gameValidations.add(new ValidateGame());
        gameValidations.add(new validateGameStatus());
        gameValidations.add(new validateTurnPlayer());
        gameValidations.add(new validateSelectedPitIndex());

        return new GameValidateCollection(gameValidations);
    }

    @Bean
    public GameValidation leaveGameValidation(){
        List<GameValidation> gameValidations = new ArrayList<>();
        gameValidations.add(new ValidateGame());
        gameValidations.add(new validateGameStatus());
        return new GameValidateCollection(gameValidations);
    }

}

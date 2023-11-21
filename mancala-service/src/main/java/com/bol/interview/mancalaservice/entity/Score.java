package com.bol.interview.mancalaservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Document("score")
public class Score extends Entity {
    private Map<Player,Integer> playersScore;

    @Indexed(unique = true)
    private String playerUsername;
}

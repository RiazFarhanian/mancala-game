package com.bol.interview.mancalaservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Document("game")
public class Game extends Entity {
    @Indexed(unique = true)
    private String joinId;
    private String turnPlayerId;
    private Status status;
    private List<Board> boardList;


    public enum Status {
        New,
        InProgress,
        Finished,
        Left
    }


}

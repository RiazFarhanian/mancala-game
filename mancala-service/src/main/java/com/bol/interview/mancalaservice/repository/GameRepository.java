package com.bol.interview.mancalaservice.repository;

import com.bol.interview.mancalaservice.entity.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {

    Game findByJoinId(String joinId);
}

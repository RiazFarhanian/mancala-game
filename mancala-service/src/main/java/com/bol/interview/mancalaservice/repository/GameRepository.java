package com.bol.interview.mancalaservice.repository;

import com.bol.interview.mancalaservice.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {

    Game findByJoinId(String joinId);

    Page<Game> findAllByStatusEquals(Game.Status status, Pageable pageable);
}

package com.bol.interview.mancalaservice.repository;

import com.bol.interview.mancalaservice.entity.Score;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends MongoRepository<Score, String> {

}

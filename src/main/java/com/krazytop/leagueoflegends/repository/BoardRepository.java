package com.krazytop.leagueoflegends.repository;

import com.krazytop.leagueoflegends.entity.Board;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardRepository extends MongoRepository<Board, String> {
}

package com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo;

import com.krazytop.leagueoflegends.domain.model.Board;
import com.krazytop.leagueoflegends.domain.port.out.board.BoardRepositoryPort;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.mapper.BoardMongoMapper;
import com.krazytop.leagueoflegends.infrastructure.adapter.out.mongo.repository.BoardMongoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class BoardMongoAdapter implements BoardRepositoryPort {

    private final BoardMongoRepository repository;
    private final BoardMongoMapper mapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Board> findBoard(String boardId) {
        return repository.findById(boardId)
                .map(mapper::toModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board saveBoard(Board board) {
        return mapper.toModel(
                repository.save(
                        mapper.toDocument(board)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBoard(String boardId) {
        repository.deleteById(boardId);
    }
}
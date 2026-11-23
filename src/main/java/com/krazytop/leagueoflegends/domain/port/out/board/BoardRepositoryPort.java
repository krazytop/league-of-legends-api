package com.krazytop.leagueoflegends.domain.port.out.board;

import com.krazytop.leagueoflegends.domain.model.Board;

import java.util.Optional;

public interface BoardRepositoryPort {

    /**
     * Récupère un board depuis la BDD.
     *
     * @param boardId l'identifiant du board
     * @return le board s'il existe
     */
    Optional<Board> findBoard(String boardId);

    /**
     * Sauvegarde ou met à jour un board en BDD.
     *
     * @param board le board à sauvegarder
     * @return le board sauvegardé
     */
    Board saveBoard(Board board);

    /**
     * Supprime un board de la BDD.
     *
     * @param boardId l'id du board à supprimer
     */
    void deleteBoard(String boardId);
}
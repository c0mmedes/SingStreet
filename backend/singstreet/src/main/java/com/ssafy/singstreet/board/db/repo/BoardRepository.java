package com.ssafy.singstreet.board.db.repo;

import com.ssafy.singstreet.board.db.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query(value = "SELECT board FROM Board board WHERE board.type = :type AND board.isDeleted = false")
    Page<Board> findByType(@Param("type") Character type,Pageable pageable);

    @Query(value = "SELECT board FROM Board board WHERE board.isDeleted = false")
    Page<Board> findAllByDeleted(Pageable pageable);

    Board findByBoardId(int boardId);

}

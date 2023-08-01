package com.ssafy.singstreet.board.db.repo;

import com.ssafy.singstreet.board.db.entity.Board;
import com.ssafy.singstreet.board.db.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Integer> {

    List<BoardComment> findBoardCommentByBoard(Board board);
}

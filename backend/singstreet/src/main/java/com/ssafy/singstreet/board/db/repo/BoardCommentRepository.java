package com.ssafy.singstreet.board.db.repo;

import com.ssafy.singstreet.board.db.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Integer> {
}

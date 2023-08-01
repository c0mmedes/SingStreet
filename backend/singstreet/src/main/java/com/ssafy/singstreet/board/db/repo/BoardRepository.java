package com.ssafy.singstreet.board.db.repo;

import com.ssafy.singstreet.board.db.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}

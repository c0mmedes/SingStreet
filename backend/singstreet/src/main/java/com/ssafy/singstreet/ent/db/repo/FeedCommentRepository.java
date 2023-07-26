package com.ssafy.singstreet.ent.db.repo;

import com.ssafy.singstreet.ent.db.entity.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Integer> {
}

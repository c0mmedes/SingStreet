package com.ssafy.singstreet.ent.db.repo;

import com.ssafy.singstreet.ent.db.entity.EntFeed;
import com.ssafy.singstreet.ent.db.entity.EntFeedComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntFeedCommentRepository extends JpaRepository<EntFeedComment, Integer> {
    List<EntFeedComment> findEntFeedCommentByFeed(EntFeed feed);
}

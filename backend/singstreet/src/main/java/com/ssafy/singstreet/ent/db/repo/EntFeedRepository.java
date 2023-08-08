package com.ssafy.singstreet.ent.db.repo;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntFeed;
import org.hibernate.sql.Select;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntFeedRepository extends JpaRepository<EntFeed, Integer> {

    EntFeed findByFeedId(int feedId);

    Slice<EntFeed> findByEnt(Ent ent, Pageable pageable);

    @Query(value = "SELECT feed FROM EntFeed feed WHERE feed.isNotice is true" )
    Slice<EntFeed> findByEntAndIsNotice(Ent ent, Pageable pageable);
    @Query(value = "SELECT feed FROM EntFeed feed WHERE feed.isNotice is false" )
    Slice<EntFeed> findByEntAndIsCommon(Ent ent, Pageable pageable);

}

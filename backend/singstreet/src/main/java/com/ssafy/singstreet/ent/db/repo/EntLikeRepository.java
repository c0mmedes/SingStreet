package com.ssafy.singstreet.ent.db.repo;

import com.ssafy.singstreet.ent.db.entity.EntFeed;
import com.ssafy.singstreet.ent.db.entity.EntLike;
import com.ssafy.singstreet.ent.db.entity.EntLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntLikeRepository extends JpaRepository<EntLike, EntLikeId> {
//    @Modifying  //delete, update시 데이터 수정을 알림
//    @Query(value = "DELETE FROM EntLike like WHERE like.id.feed = :feed")
//    void deleteAllByIdEntFeed(@Param("feed") EntFeed feed);

    List<EntLike> findAllByIdFeed(EntFeed feed);


}

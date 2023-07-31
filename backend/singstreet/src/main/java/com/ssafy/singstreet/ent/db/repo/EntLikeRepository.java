package com.ssafy.singstreet.ent.db.repo;

import com.ssafy.singstreet.ent.db.entity.EntLike;
import com.ssafy.singstreet.ent.db.entity.EntLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntLikeRepository extends JpaRepository<EntLike, EntLikeId> {
}

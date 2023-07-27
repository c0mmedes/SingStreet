package com.ssafy.singstreet.ent.db.repo;

import com.ssafy.singstreet.ent.db.entity.Ent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntRepository extends JpaRepository<Ent, Integer> {
    Ent findByEntId(int entId);
}

package com.ssafy.singstreet.ent.db.repo;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntTagRepository extends JpaRepository<EntTag, Integer> {

    List<EntTag> findAllByEntId(Ent entId);
    //FK면 위와같이 객체를 써야한다.(!PK아님 주의!)

}

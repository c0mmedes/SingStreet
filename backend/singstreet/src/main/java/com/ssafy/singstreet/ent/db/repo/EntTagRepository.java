package com.ssafy.singstreet.ent.db.repo;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntTagRepository extends JpaRepository<EntTag, Integer> {

    List<EntTag> findAllByEntId(Ent ent);
    //FK면 위와같이 객체를 써야한다.(!PK아님 주의!)

    @Query(value = "SELECT tag " +
                "FROM EntTag tag " +
                "WHERE tag.entId " +
                "IN :entIdList ")
    List<EntTag> findByEntIdList(@Param("entIdList") List<Ent> entIdList);
}

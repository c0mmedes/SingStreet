package com.ssafy.singstreet.ent.db.repo;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.user.db.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntRepository extends JpaRepository<Ent, Integer> {

    Ent findByEntId(int entId);

    Slice<Ent> findByIsDeleted(Boolean isDeleted, Pageable pageable);

    List<Ent> findByUserAndIsDeleted(User user,boolean isDeleted);

    List<Ent> findAllByUser(User user);


}

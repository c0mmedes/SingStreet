package com.ssafy.singstreet.ent.db.repo;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntMember;
import com.ssafy.singstreet.ent.db.entity.EntMemberId;
import com.ssafy.singstreet.user.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntMemberRepository extends JpaRepository<EntMember, EntMemberId> {
    List<EntMember> findAllByEnt(Ent ent);
    EntMember findByMemberId(int memberID);

//    @Query(value = "SELECT member.ent FROM EntMember member WHERE member.user = :user AND member.isDeleted is false" )
//    List<Ent> findMyEnt(@Param("user") User user);

    List<EntMember> findAllByUserAndIsDeleted(User user, Boolean isDeleted);
}

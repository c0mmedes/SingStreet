package com.ssafy.singstreet.ent.db.repo;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntMember;
import com.ssafy.singstreet.ent.db.entity.EntMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntMemberRepository extends JpaRepository<EntMember, EntMemberId> {
    List<EntMember> findAllByEnt(Ent ent);
    EntMember findByMemberId(int memberID);
}

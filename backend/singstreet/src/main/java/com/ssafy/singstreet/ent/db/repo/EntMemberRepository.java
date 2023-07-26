package com.ssafy.singstreet.ent.db.repo;

import com.ssafy.singstreet.ent.db.entity.EntMember;
import com.ssafy.singstreet.ent.db.entity.EntMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntMemberRepository extends JpaRepository<EntMember, EntMemberId> {
}

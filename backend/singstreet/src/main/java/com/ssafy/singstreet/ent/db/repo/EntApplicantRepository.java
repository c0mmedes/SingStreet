package com.ssafy.singstreet.ent.db.repo;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntApplicant;
import com.ssafy.singstreet.user.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntApplicantRepository extends JpaRepository<EntApplicant, Integer> {
    EntApplicant findEntApplicantByApplId(int applId);

    EntApplicant findEntApplicantByEntIdAndUserId(Ent entId, User userId);

    List<EntApplicant> findEntApplicantsByEntIdAndIsConfirmed(Ent entId, boolean isConfirmed);
}

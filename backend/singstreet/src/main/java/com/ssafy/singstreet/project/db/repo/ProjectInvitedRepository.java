package com.ssafy.singstreet.project.db.repo;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectInvited;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectInvitedRepository extends JpaRepository<ProjectInvited, Integer> {
    Optional<Object> findByProjectProjectId(Integer projectId);

    List<ProjectInvited> findByUserUserId(Integer userId);

    @Query("SELECT pi FROM ProjectInvited pi WHERE pi.project.projectId = :projectId")
    List<ProjectInvited> findByProjectId(@Param("projectId") Integer projectId);

    Boolean existsByUserUserIdAndIsAcceptedIsTrue(Integer userId);
}

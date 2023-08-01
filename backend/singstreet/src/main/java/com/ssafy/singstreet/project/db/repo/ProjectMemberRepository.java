package com.ssafy.singstreet.project.db.repo;

import com.ssafy.singstreet.project.db.entity.ProjectMember;
import com.ssafy.singstreet.project.db.entity.ProjectMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {
    List<ProjectMember> findByProjectMemberIdProjectProjectId(Integer projectId);


}

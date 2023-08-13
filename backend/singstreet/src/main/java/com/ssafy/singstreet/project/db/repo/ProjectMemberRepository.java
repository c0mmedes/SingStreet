package com.ssafy.singstreet.project.db.repo;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectMember;
import com.ssafy.singstreet.project.db.entity.ProjectMemberId;
import com.ssafy.singstreet.user.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, ProjectMemberId> {
//    List<ProjectMember> findByProjectMemberIdProjectProjectId(Integer projectId);
    @Query("SELECT pm FROM ProjectMember pm WHERE pm.isDeleted = false AND pm.projectMemberId.project.projectId = :projectId")
    List<ProjectMember> findByProjectMemberIdProjectProjectIdAndIsDeletedIsFalse(Integer projectId);


    // projectId와 userId에 해당하는 프로젝트 멤버를 찾아주는 메서드
    @Query(value = "SELECT * FROM project_member WHERE project_id = ?1 AND user_id = ?2", nativeQuery = true)
    ProjectMember findByProjectIdAndUserId(Integer projectId, Integer userId);

    List<ProjectMember> findByProjectMemberId_Project(Project project);
}

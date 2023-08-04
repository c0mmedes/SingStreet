package com.ssafy.singstreet.project.db.repo;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectLikes;
import com.ssafy.singstreet.project.db.entity.ProjectLikesId;
import com.ssafy.singstreet.user.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectLikeRepository extends JpaRepository<ProjectLikes, ProjectLikesId> {
    ProjectLikes findById_ProjectAndId_User(Project project, User user);

    void deleteById_User_UserIdAndId_Project_ProjectId(Integer userId, Integer projectId);

    List<ProjectLikes> findById_User(User user);
}

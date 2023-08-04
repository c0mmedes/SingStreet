package com.ssafy.singstreet.project.db.repo;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectCommentRepository extends JpaRepository<ProjectComment, Integer> {
    Page<ProjectComment> findByProjectOrderByCreatedAtDesc(Project project, Pageable pageable);
}
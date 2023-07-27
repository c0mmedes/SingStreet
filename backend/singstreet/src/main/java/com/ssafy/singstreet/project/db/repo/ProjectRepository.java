package com.ssafy.singstreet.project.db.repo;

import com.ssafy.singstreet.project.db.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}

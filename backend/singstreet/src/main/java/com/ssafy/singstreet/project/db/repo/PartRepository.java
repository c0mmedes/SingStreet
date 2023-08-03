package com.ssafy.singstreet.project.db.repo;

import com.ssafy.singstreet.project.db.entity.Part;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Integer> {
//    List<Part> findAllByProjectId(Project projectId);
}

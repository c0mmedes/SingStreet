package com.ssafy.singstreet.project.db.repo;

import com.ssafy.singstreet.project.db.entity.Part;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectTag;
import com.ssafy.singstreet.project.model.ProjectPartDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Integer> {
    @Query("SELECT p FROM Part p WHERE p.project.projectId = :projectId")
    List<Part> findAllByProjectId(@Param("projectId") Integer projectId);
}

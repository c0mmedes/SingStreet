package com.ssafy.singstreet.project.db.repo;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectTagRepository extends JpaRepository<ProjectTag, Integer> {
    List<ProjectTag> findAllByProjectId(Project projectId);
    List<ProjectTag> findByTagName(String tag);
    //FK면 위와같이 객체를 써야한다.(!PK아님 주의!)

   /* @Query(value = "SELECT tag " +
                "FROM ProjectTag tag " +
                "WHERE tag.projectId " +
                "IN :projectIdList ")
    List<ProjectTag> findByProjectIdList(@Param("projectIdList") List<Project> projectIdList);*/


}

package com.ssafy.singstreet.project.db.repo;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.user.db.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    // 프로젝트id로 상세조회
    Project findByProjectId(int ProjectId);
    // keyword 검색
//    @Query("SELECT p FROM Project p WHERE " +
//            "p.projectName LIKE %:keyword% OR " +
//            "p.projectInfo LIKE %:keyword% OR " +
//            "p.singerName LIKE %:keyword% OR " +
//            "p.singName LIKE %:keyword% OR " +
//            "p.ent.entName Like %:keyword%"
//    )
//    Page<Project> findByProjectNameContainingOrDescriptionContaining(String keyword, Pageable pageable);

    @Query("SELECT p FROM Project p WHERE " +
            "p.isDestroyed = false AND " +
            "(p.projectName LIKE %:keyword% OR " +
            "p.projectInfo LIKE %:keyword% OR " +
            "p.singerName LIKE %:keyword% OR " +
            "p.singName LIKE %:keyword% OR " +
            "p.ent.entName LIKE %:keyword%)"
    )
    Page<Project> findByIsDestroyedFalseAndKeyword(@Param("keyword") String keyword, Pageable pageable);

    Page<Project> findByIsDestroyedFalse(Pageable pageable);

    List<Project> findByUser(User user);

    List<Project> findByEnt(Ent ent);

    List<Project> findByProjectIdIn(List<Integer> projectIds);

    List<Project> findAllByOrderByMonthlyLikeCountDesc();
}

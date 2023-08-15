package com.ssafy.singstreet.studio.db.repo;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.studio.db.entity.AudioBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AudioBlockRepository extends JpaRepository<AudioBlock, Integer> {
    List<AudioBlock> findByProject_ProjectIdAndIsDeletedFalse(Project projectId);

    AudioBlock findByBlockId(int blockId);

    // 새로운 메서드 추가
    @Query(nativeQuery = true, value = "SELECT * FROM audio_block WHERE project_id = ?1 AND is_deleted = false OFFSET ?2")
    List<AudioBlock> findByProjectIdAndIsDeletedFalseWithOffset(int projectId, int offset);
}

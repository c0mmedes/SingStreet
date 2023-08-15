package com.ssafy.singstreet.studio.db.repo;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.studio.db.entity.AudioBlock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AudioBlockRepository extends JpaRepository<AudioBlock, Integer> {
    List<AudioBlock> findByProject_ProjectIdAndIsDeletedFalse(Project projectId);

    AudioBlock findByBlockId(int blockId);

}

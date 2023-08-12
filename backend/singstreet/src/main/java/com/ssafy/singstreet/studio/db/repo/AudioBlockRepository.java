package com.ssafy.singstreet.studio.db.repo;

import com.ssafy.singstreet.studio.db.entity.AudioBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AudioBlockRepository extends JpaRepository<AudioBlock, Integer> {
}

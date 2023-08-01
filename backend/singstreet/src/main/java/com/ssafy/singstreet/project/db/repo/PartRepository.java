package com.ssafy.singstreet.project.db.repo;

import com.ssafy.singstreet.project.db.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Integer> {
}

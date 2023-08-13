package com.ssafy.singstreet.studio.db.repo;

import com.ssafy.singstreet.studio.db.entity.BoxData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BoxDataRepository extends MongoRepository<BoxData, String> {
    List<BoxData> findByProjectId(String projectId);
}
package com.ssafy.singstreet.studio.service;

import com.ssafy.singstreet.studio.db.entity.BoxData;
import com.ssafy.singstreet.studio.db.repo.BoxDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BoxDataService {

    @Autowired
    private BoxDataRepository boxDataRepository;

    // box 데이터 저장
    public void saveBoxData(BoxData boxData) {
        boxDataRepository.save(boxData);
    }

    // projectId에 해당하는 모든 box 데이터 조회
    public List<BoxData> getBoxDataByProjectId(String projectId) {
        return boxDataRepository.findByProjectId(projectId);
    }

}

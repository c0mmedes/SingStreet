package com.ssafy.singstreet.studio.controller;

import com.ssafy.singstreet.studio.db.entity.BoxData;
import com.ssafy.singstreet.studio.service.BoxDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/box")
@RequiredArgsConstructor
public class BoxDataController {
    private final BoxDataService boxDataService;

    @GetMapping("/{projectId}")
    public List<BoxData> getBoxDataByProjectId(@PathVariable String projectId) {
        return boxDataService.getBoxDataByProjectId(projectId);
    }

    @PostMapping("/")
    public void saveBoxData(@RequestBody BoxData boxData) {
        boxDataService.saveBoxData(boxData);
    }
}



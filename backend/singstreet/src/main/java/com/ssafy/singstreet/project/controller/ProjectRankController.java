package com.ssafy.singstreet.project.controller;

import com.ssafy.singstreet.project.model.ProjectRankDto;
import com.ssafy.singstreet.project.service.ProjectRankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class ProjectRankController {

    private final ProjectRankService projectRankService;

    @GetMapping
    public ResponseEntity<List<ProjectRankDto>> getTop10RankingsForLastMonthWithProjects() {
        projectRankService.updateMonthlyRanking();
        List<ProjectRankDto> ProjectRankDtos= projectRankService.getTop10RankingsForLastMonthWithProjects();
        System.out.println(ProjectRankDtos);
        if (ProjectRankDtos == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ProjectRankDtos, HttpStatus.OK);
    }
}
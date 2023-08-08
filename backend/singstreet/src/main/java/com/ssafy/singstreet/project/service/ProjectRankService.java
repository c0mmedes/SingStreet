package com.ssafy.singstreet.project.service;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.Rank;
import com.ssafy.singstreet.project.db.repo.ProjectRankRepository;
import com.ssafy.singstreet.project.db.repo.ProjectRepository;
import com.ssafy.singstreet.project.model.ProjectRankDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectRankService {

    private final ProjectRepository projectRepository;
    private final ProjectRankRepository rankRepository;

//    0 0 1: 시간을 1로 설정하고, 분과 초를 0으로 설정하여 정확히 1시 0분 0초에 실행하도록 지정
//    * * ?: 일, 월, 요일은 모두 "모든" 값을 의미. 따라서 매월 1일에 실행
    @Scheduled(cron = "0 0 1 * * ?") // 매달 1일 0시 0분에 실행
    public void updateMonthlyRanking() {
        // 전달 좋아요 수 계산 및 업데이트
        List<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            int currentMonthlyLikes = project.getLikeCount() - project.getMonthlyLikeCount();
            project.updateMonthlyLikeCount(currentMonthlyLikes);
            projectRepository.save(project);
        }

        // 랭킹 업데이트
        List<Project> rankedProjects = projectRepository.findAllByOrderByMonthlyLikeCountDesc();
        for (int i = 0; i < rankedProjects.size(); i++) {
            Project project = rankedProjects.get(i);
            Rank rank = Rank.builder()
                    .project(project)
                    .month(LocalDate.now().minusMonths(1)) // 지난 달의 랭킹 정보 저장
                    .likeCount(project.getMonthlyLikeCount())
                    .ranking(i + 1) // 순위 정보 저장 (1부터 시작)
                    .build();
            rankRepository.save(rank);
        }
    }

    @Scheduled(cron = "0 5 1 * * ?") // 매달 1일 0시 5분에 실행
    public List<ProjectRankDto> getTop10RankingsForLastMonthWithProjects() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1).withDayOfMonth(1);
        LocalDate lastMonthLastDay = LocalDate.now().minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());

        List<Rank> rankings = rankRepository.findByMonthBetweenOrderByRankingAsc(lastMonth, lastMonthLastDay);

        List<ProjectRankDto> ranksWithProjects = new ArrayList<>();
        for (int i = 0; i < Math.min(rankings.size(), 10); i++) {
            Rank rank = rankings.get(i);
            Project project = projectRepository.findById(rank.getProject().getProjectId()).orElse(null);
            if (project != null) {
                ProjectRankDto dto = ProjectRankDto.builder()
                        .projectId(project.getProjectId())
                        .entName(project.getEnt().getEntName())
                        .singerName(project.getSingerName())
                        .singName(project.getSingName())
                        .projectImg(project.getProjectImg())
                        .likeCount(project.getMonthlyLikeCount())
                        .build();
                ranksWithProjects.add(dto);
            }
        }
        return ranksWithProjects;
    }
}

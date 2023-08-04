package com.ssafy.singstreet.project.db.repo;

import com.ssafy.singstreet.project.db.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRankRepository extends JpaRepository <Rank, Integer> {

//    List<Rank> findByMonthOrderByRankingAsc(LocalDate currentMonth);

    List<Rank> findByMonthBetweenOrderByRankingAsc(LocalDate lastMonth, LocalDate lastMonthLastDay);
}

package com.ssafy.singstreet.project.db.entity;

import com.ssafy.singstreet.user.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@NoArgsConstructor // 기본 생성자
@Builder // 생성자 만들기
@AllArgsConstructor // 모든 필드를 사용하는 생성자
@Entity
@Table(name = "ranking")
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rank_id")
    private Integer rankId;

    @ManyToOne
    @JoinColumn(name = "project_id" , nullable = false)
    private Project project;

//    @Column(name = "project_id", nullable = false)
//    private Integer projectId;

    @Column(name = "month", nullable = false)
    private LocalDate month;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "ranking")
    private Integer ranking;
}

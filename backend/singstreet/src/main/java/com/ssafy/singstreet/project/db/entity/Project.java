package com.ssafy.singstreet.project.db.entity;

import com.ssafy.singstreet.common.BaseTimeEntity;
import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.user.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // 기본 생성자
@Builder // 생성자 만들기
@AllArgsConstructor // 모든 필드를 사용하는 생성자
@Entity
@Table(name = "project")
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer projectId;

    @ManyToOne
    @JoinColumn(name = "ent_id" , nullable = false)
    private Ent ent;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

//    @Column(name = "ent_id", nullable = false)
//    private Integer entId; // Assuming ent_id references the ent table's ent_id
//
//    @Column(name = "user_id", nullable = false)
//    private Integer userId; // Assuming user_id references the user table's user_id

    @Column(name = "project_name", nullable = false, length = 20)
    private String projectName;

    @Column(name = "singer_name", nullable = false, length = 20)
    private String singerName;

    @Column(name = "sing_name", nullable = false, length = 20)
    private String singName;

    @Column(name = "project_info", length = 1000)
    private String projectInfo;

    @Column(name = "project_img", length = 255)
    private String projectImg;

    @Column(name = "like_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int likeCount;

    @Column(name = "hit_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int hitCount;

    @Column(name = "monthly_like_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int monthlyLikeCount;

    @Column(name = "is_completed", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isCompleted;

    @Column(name = "is_destroyed", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isDestroyed;

    @Column(name = "origin_filename", length = 255)
    private String originFilename;

    @Column(name = "last_enter_date")
    private LocalDate lastEnterDate;

//    @Column(name = "created_at", nullable = false)
//    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "is_visible", nullable = false)
    private boolean isVisible;

    @Column(name = "audio_name", length = 255)
    private String audioName;
}

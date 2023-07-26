package com.ssafy.singstreet.ent.db.entity;

import com.ssafy.singstreet.common.BaseTimeEntity;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.user.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // 기본 생성자
@Entity
@Table(name = "ent_feed")
public class EntFeed extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Integer feedId;

    @ManyToOne
    @JoinColumn(name = "ent_id" , nullable = false)
    private Ent ent;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

//    @Column(name = "user_id", nullable = false)
//    private Integer userId; // Assuming user_id references the user table's user_id
//
//    @Column(name = "ent_id", nullable = false)
//    private Integer entId; // Assuming ent_id references the ent table's ent_id
//
//    @Column(name = "project_id", nullable = false)
//    private Integer projectId; // Assuming project_id references the project table's project_id

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "type", nullable = false)
    private Byte type;

    @Column(name = "hit_count", nullable = false)
    private Integer hitCount;

//    @Column(name = "created_at", nullable = false)
//    private LocalDateTime createdAt;

}

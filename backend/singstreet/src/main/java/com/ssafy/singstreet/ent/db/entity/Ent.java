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

@Entity
@Table(name = "ent")
@Getter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 사용하는 생성자
@Builder // 생성자 만들기
public class Ent extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ent_id")
    private Integer entId;


    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

//    @Column(name = "user_id", nullable = false)
//    private Integer userId; // Assuming user_id references the user table's user_id

    @Column(name = "ent_name", nullable = false, length = 20)
    private String entName;

    @Column(name = "is_auto_accepted", nullable = false, length = 40)
    private String isAutoAccepted;

    @Column(name = "ent_info", length = 1000)
    private String entInfo;

    @Column(name = "ent_img", length = 255)
    private String entImg;

//    @Column(name = "created_at", nullable = false)
//    private LocalDateTime createdAt;
}

package com.ssafy.singstreet.ent.db.entity;

import com.ssafy.singstreet.common.BaseTimeEntity;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.user.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // 기본 생성자
@Entity
@Table(name = "ent_applicant")
public class EntApplicant extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appl_id")
    private Integer applId;

//    @Column(name = "ent_id", nullable = false)
//    private Integer entId; // Assuming ent_id references the ent table's ent_id

    @ManyToOne
    @JoinColumn(name = "ent_id" , nullable = false)
    private Ent ent;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}

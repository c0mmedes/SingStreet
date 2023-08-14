package com.ssafy.singstreet.project.db.entity;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.user.db.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // 기본 생성자
@Builder // 생성자 만들기
@AllArgsConstructor // 모든 필드를 사용하는 생성자
@Entity
@Setter
@Table(name = "project_invited")
public class ProjectInvited {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_member_id")
    private Integer projectMemberId;

    @ManyToOne
    @JoinColumn(name = "project_id" , nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "ent_id" , nullable = false)
    private Ent ent;

    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "confirm_date")
    private LocalDateTime confirmDate;

    public void accept() {
        this.isAccepted = true;
    }

    public void reject() {
        this.isAccepted = false;
    }

    public void updateConfirmDate() {
        this.confirmDate = LocalDateTime.now();
    }
}

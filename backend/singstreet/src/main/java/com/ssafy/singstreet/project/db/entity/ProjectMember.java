package com.ssafy.singstreet.project.db.entity;

import com.ssafy.singstreet.user.db.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // 기본 생성자
@Builder // 생성자 만들기
@AllArgsConstructor // 모든 필드를 사용하는 생성자
@Entity
@Setter
@Table(name = "project_member")
public class ProjectMember {

    @EmbeddedId
    private ProjectMemberId projectMemberId;

    @Column(name = "is_leader", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isLeader;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isDeleted;

    // 회원 탈퇴
    public void leave() {
        this.isDeleted = true;
    }


    public User getUser() {
        return projectMemberId.getUser();
    }
}



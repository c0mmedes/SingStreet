package com.ssafy.singstreet.ent.db.entity;

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
@Table(name = "ent_member")
public class EntMember {

    @EmbeddedId
    private EntMemberId entMemberId;

    @Column(name = "is_leader", nullable = false)
    private boolean isLeader;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
}

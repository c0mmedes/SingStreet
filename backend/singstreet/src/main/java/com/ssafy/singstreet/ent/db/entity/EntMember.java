package com.ssafy.singstreet.ent.db.entity;

import com.ssafy.singstreet.common.BaseTimeEntity;
import com.ssafy.singstreet.user.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // 기본 생성자
@Entity
@Table(name = "ent_member")
@EntityListeners(AuditingEntityListener.class)
public class EntMember extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer memberId;

    @ManyToOne
    @JoinColumn(name = "ent_id" , nullable = false)
    private Ent ent;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @Column(name = "is_leader",nullable = false, columnDefinition = "BOOLEAN default false")
    private boolean isLeader;

    @Column(name = "is_deleted",nullable = false, columnDefinition = "BOOLEAN default false")
    private boolean isDeleted;

    @Builder
    public EntMember(Ent ent, User user, boolean isLeader){
        this.ent = ent;
        this.user = user;
        this.isLeader = isLeader;
    }

    public void delete(){
        this.isDeleted = true;
    }
}

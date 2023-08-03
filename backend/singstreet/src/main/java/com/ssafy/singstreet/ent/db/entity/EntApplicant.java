package com.ssafy.singstreet.ent.db.entity;

import com.ssafy.singstreet.common.BaseTimeEntity;
import com.ssafy.singstreet.project.db.entity.Project;
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
@EntityListeners(AuditingEntityListener.class)
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
    private Ent entId;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User userId;

    @Column(name = "hope", nullable = false, length = 30)
    private String hope;

    @Column(name = "artist", nullable = false, length = 30)
    private String artist;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "audio_name", length = 30)
    private String audioName;

    @Column(name = "is_confirmed", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isConfirmed;

    @Column(name = "is_accepted")
    private Boolean isAccepted;

    @Builder
    public EntApplicant(Ent entId, User userId, String hope, String artist, Integer age,String content,String audioName,Boolean isConfirmed,Boolean isAccepted){
        this.entId = entId;
        this.userId = userId;
        this.hope=hope;
        this.artist = artist;
        this.age = age;
        this.content = content;
        this.audioName = audioName;
        this.isConfirmed = isConfirmed;
        this.isAccepted = isAccepted;
    }

    public void accept(){
        this.isConfirmed = true;
        this.isAccepted = true;
    }
}

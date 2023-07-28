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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ent")
@Getter
@NoArgsConstructor // 기본 생성자
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
    private Boolean isAutoAccepted;

    @Column(name = "ent_info", length = 1000)
    private String entInfo;

    @Column(name = "ent_img", length = 255)
    private String entImg;

    @Column(name = "is_deleted", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isDeleted;

    @Builder
    public Ent(User user, String entName, Boolean isAutoAccepted, String entInfo, String entImg){
        this.user = user;
        this.entName = entName;
        this.isAutoAccepted = isAutoAccepted;
        this.entInfo = entInfo;
        this.entImg = entImg;
    }

    public void update(String entName, Boolean isAutoAccepted, String entInfo, String entImg){
        this.entName = entName;
        this.isAutoAccepted = isAutoAccepted;
        this.entInfo = entInfo;
        this.entImg = entImg;
    }

    public void delete(){
        this.isDeleted = true;
    }

}

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

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "is_notice", columnDefinition = "BOOLEAN default false")
    private Boolean isNotice;

    @Column(name = "file_name")
    private String fileName;

    @Builder
    public EntFeed(Ent ent, User user, String title, String content, Boolean isNotice, String fileName){
        this.ent = ent;
        this.user = user;
        this.title = title;
        this.content = content;
        this.isNotice = isNotice;
        this.fileName = fileName;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void updateFileName(String fileName){
        this.fileName=fileName;
    }

}

package com.ssafy.singstreet.board.db.entity;
import com.ssafy.singstreet.admin.db.entity.Admin;
import com.ssafy.singstreet.common.BaseTimeEntity;
import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.user.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // 기본 생성자
@Builder // 생성자 만들기
@AllArgsConstructor // 모든 필드를 사용하는 생성자
@Entity
@Table(name = "board")
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Integer boardId;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "type", nullable = false)
    private Character type;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "hit_count",columnDefinition = "Integer default 0")
    private Integer hitCount;

    @Column(name = "answer_text", length = 1000)
    private String answerText;

    @Column(name = "answerd_at")
    private LocalDateTime anseredAt;

    @Column(name = "is_deleted",columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isDeleted;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    public void update(String title, String content){
        this.title = title;
        this.content =content;
    }

    public void delete(){
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

}

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
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @Column(name = "user_id", nullable = false)
//    private Integer userId; // Assuming user_id references the user table's user_id
//
//    @Column(name = "admin_id")
//    private Integer adminId;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "type", nullable = false)
    private Byte type;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "hit_count", nullable = false)
    private Integer hitCount;

//    @Column(name = "created_at", nullable = false)
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;

    @Column(name = "answer_text", length = 1000)
    private String answerText;

    @Column(name = "date_answered", nullable = false)
    private LocalDateTime dateAnswered;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isDeleted;

}

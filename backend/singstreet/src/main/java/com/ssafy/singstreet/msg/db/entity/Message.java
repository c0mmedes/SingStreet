package com.ssafy.singstreet.msg.db.entity;

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
@Builder // 생성자 만들기
@AllArgsConstructor // 모든 필드를 사용하는 생성자
@Entity
@Table(name = "msg")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id")
    private Integer msgId;

    @ManyToOne
    @JoinColumn(name = "msg_reciever", referencedColumnName = "user_id" , nullable = false)
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "msg_sender", referencedColumnName = "user_id" , nullable = false)
    private User sender;

//    @Column(name = "msg_receiver", nullable = false)
//    private Integer msgReceiver;
//
//    @Column(name = "msg_sender", nullable = false)
//    private Integer msgSender;

    @Column(name = "msg_title", nullable = false, length = 20)
    private String msgTitle;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "reply_id")
    private Integer replyId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "is_confirmed")
    private Boolean isConfirmed;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
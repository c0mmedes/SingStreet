package com.ssafy.singstreet.msg.db.entity;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.user.db.entity.User;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 사용하는 생성자
@Builder
@Entity
@DynamicInsert //default를 위헤
@EntityListeners(AuditingEntityListener.class)
@Table(name = "msg")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id")
    private Integer msgId;

    @ManyToOne
    @JoinColumn(name = "msg_receiever", referencedColumnName = "user_id" , nullable = false)
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "msg_sender", referencedColumnName = "user_id" , nullable = false)
    private User sender;

    @Column(name = "msg_title", nullable = false, length = 20)
    private String msgTitle;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "reply_id")
    private Integer replyId;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed;

    @Column(name = "receiever_deleted", nullable = false)
    private Boolean receiverDeleted;

    @Column(name = "sender_deleted", nullable = false)
    private Boolean senderDeleted;

    @PrePersist
    private void prePersist(){
        if(isConfirmed == null){
            isConfirmed = false;
        }
        if (receiverDeleted == null){
            receiverDeleted = false;
        }
        if (senderDeleted == null){
            senderDeleted = false;
        }
    }

    public void updateReplyId(int replyId){
        this.replyId = replyId;
    }
    public void updateSenderDeleted(){this.senderDeleted = true;}
    public void updateReceiverDeleted(){this.receiverDeleted = true;}
    public void updateIsConfirmed(){this.isConfirmed = true;}
}
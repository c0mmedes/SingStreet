package com.example.demo.chatt.db;//package com.ssafy.singstreet.chatt.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EntityListeners;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)  //Auditing기능(시간 자동으로 값 넣어주는..) 포함
@Document(collection = "messages")
public class ChatMessage {
    @Id
    private String id;
    private int entId;
    private String nickname;
    private String content;
    @CreatedDate
    private Date createdAt;

    public void updateDate(){
        this.createdAt = new Date();
    }
}

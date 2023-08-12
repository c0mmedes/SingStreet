package com.ssafy.singstreet.studio.db.entity;

import com.ssafy.singstreet.project.db.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // 기본 생성자
@Builder // 생성자 만들기
@AllArgsConstructor // 모든 필드를 사용하는 생성자
@Entity
@Table(name = "audio_block")
public class AudioBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id")
    private Integer blockId;

    @ManyToOne
    @JoinColumn(name = "project_id" , nullable = false)
    private Project project;

    @Column(name = "user_id" , nullable = false)
    private Integer userId;

    @Column(name = "time_line", nullable = false)
    private Float timeLine;

    @Column(name = "x_Pos")
    private Float xPos;

    @Column(name = "y_Pos")
    private Float yPos;

    @Column(name = "block_name", nullable = false, length = 20)
    private String blockName;

//    @CreatedDate
//    @Column(name = "created_at", nullable = false)
//    private LocalDateTime createdAt;
}

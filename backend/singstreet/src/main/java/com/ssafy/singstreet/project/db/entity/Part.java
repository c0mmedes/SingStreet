package com.ssafy.singstreet.project.db.entity;

import com.ssafy.singstreet.user.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor // 기본 생성자
@Builder // 생성자 만들기
@AllArgsConstructor // 모든 필드를 사용하는 생성자
@Entity
@Table(name = "part")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "part_id")
    private Integer partId;

    @ManyToOne
    @JoinColumn(name = "project_id" , nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "part_name", length = 30)
    private String partName;

    // 파트 업데이트
    public void update(Integer partId, User user, String partName){
        this.partId = partId;
        this.user = user;
        this.partName = partName;
    }

}


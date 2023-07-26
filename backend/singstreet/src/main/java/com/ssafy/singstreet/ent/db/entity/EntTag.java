package com.ssafy.singstreet.ent.db.entity;

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
@Table(name = "ent_tag")
public class EntTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ent_tag_id")
    private Integer entTagId;

    @ManyToOne
    @JoinColumn(name = "ent_id" , nullable = false)
    private Ent ent;

//    @Column(name = "ent_id", nullable = false)
//    private Integer entId; // Assuming ent_id references the ent table's ent_id

    @Column(name = "tag_name", nullable = false, length = 20)
    private String tagName;
}

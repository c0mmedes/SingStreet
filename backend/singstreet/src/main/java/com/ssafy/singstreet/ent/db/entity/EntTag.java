package com.ssafy.singstreet.ent.db.entity;

import com.ssafy.singstreet.user.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor // 기본 생성자
@Entity
@Table(name = "ent_tag")
public class EntTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ent_tag_id")
    private Integer entTagId;

    @ManyToOne
    @JoinColumn(name = "ent_id" , nullable = false)
    private Ent entId;

//    @Column(name = "ent_id", nullable = false)
//    private Integer entId; // Assuming ent_id references the ent table's ent_id

    @Column(name = "tag_name", nullable = false, length = 20)
    private String tagName;

    @Builder
    public EntTag(Ent entId, String tagName){
        this.entId = entId;
        this.tagName = tagName;
    }
}

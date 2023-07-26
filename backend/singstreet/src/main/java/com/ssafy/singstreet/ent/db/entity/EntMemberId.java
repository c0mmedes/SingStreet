package com.ssafy.singstreet.ent.db.entity;

import com.ssafy.singstreet.user.db.entity.User;

import javax.persistence.*;
import java.io.Serializable;


@Embeddable
public class EntMemberId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "ent_id" , nullable = false)
    private Ent ent;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

//    @Column(name = "user_id")
//    private Integer userId; // Assuming user_id references the user table's user_id
//
//    @Column(name = "ent_id")
//    private Integer entId; // Assuming ent_id references the ent table's ent_id

}

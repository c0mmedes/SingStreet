package com.ssafy.singstreet.like.db.entity;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.user.db.entity.User;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LikesId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "project_id" , nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;
}

package com.ssafy.singstreet.project.db.entity;

import com.ssafy.singstreet.user.db.entity.User;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class ProjectMemberId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "project_id" , nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

//    @Column(name = "user_id")
//    private Integer userId;
//
//    @Column(name = "project_id")
//    private Integer projectId;

}



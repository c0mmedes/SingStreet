package com.ssafy.singstreet.user.db.entity;

import com.ssafy.singstreet.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 사용하는 생성자
@Builder // 생성자 만들기
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "nickname", nullable = false, length = 8)
    private String nickname;

    @Column(name = "user_img", length = 255)
    private String userImg;

    @Column(name = "email", nullable = false, length = 254)
    private String email;

    @Column(name = "gender", nullable = false, length = 1)
    private String gender;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "jwt_token", length = 255)
    private String jwtToken;

    @Column(name = "refresh_token", length = 255)
    private String refreshToken;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
package com.ssafy.singstreet.admin.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // 기본 생성자
@Builder // 생성자 만들기
@AllArgsConstructor // 모든 필드를 사용하는 생성자
@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Integer adminId;

    @Column(name = "id", nullable = false, length = 20)
    private String id;

    @Column(name = "password", nullable = false, length = 20)
    private String password;

    @Column(name = "jwt_token", nullable = false, length = 255)
    private String jwtToken;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}

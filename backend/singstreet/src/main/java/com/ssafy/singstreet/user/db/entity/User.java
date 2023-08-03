package com.ssafy.singstreet.user.db.entity;

import com.ssafy.singstreet.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 사용하는 생성자
@Builder // 생성자 만들기
public class User extends BaseTimeEntity implements UserDetails {
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
    private Character gender;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "jwt_token", length = 255)
    private String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Column(name = "refresh_token", length = 255)
    private String refreshToken;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public void updatePassword(String newPassword) {
        // You can add any password validation or hashing logic here before updating the password.
        this.password = newPassword;
    }
    public void updateUserInfo(String newNickname, String newUserImg, Character newGender, String newPassword) {
        this.nickname = newNickname;
        this.userImg = newUserImg;
        this.gender = newGender;
        this.password=newPassword;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
    public void setDeletedAt(LocalDateTime l){
        deletedAt=l;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isDeleted;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
package com.ssafy.singstreet.user.db.repo;

import com.ssafy.singstreet.user.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    User findByEmail(String email);

    User findByEmailAndIsDeleted(String email,boolean isDeleted);

    User findByUserId(Integer userId);
}

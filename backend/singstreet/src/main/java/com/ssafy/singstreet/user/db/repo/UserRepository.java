package com.ssafy.singstreet.user.db.repo;

import com.ssafy.singstreet.user.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    User findByEmail(String email);

    User findByUserId(Integer userId);
}

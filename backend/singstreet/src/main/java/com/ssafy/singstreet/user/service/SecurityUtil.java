package com.ssafy.singstreet.user.service;

import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RequiredArgsConstructor
public class SecurityUtil {
    private final UserRepository userRepository;

    public static String getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication information.");
        }else {
            return authentication.getName();
        }
    }

    public int getCurrentUserId(){
        return userRepository.findByEmail(getCurrentMemberId()).getUserId();
    }
}

package com.ssafy.singstreet.ent.service;

import com.ssafy.singstreet.ent.db.entity.EntFeed;
import com.ssafy.singstreet.ent.db.repo.EntFeedRepository;
import com.ssafy.singstreet.ent.db.repo.EntMemberRepository;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.ent.model.entFeedDto.EntFeedSaveRequestDto;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EntFeedService {
    private final EntRepository repository;
    private final UserRepository userRepository;
    private final EntFeedRepository feedRepository;

    @Transactional
    public boolean saveFeed(EntFeedSaveRequestDto requestDto){
        EntFeed feed = EntFeed.builder()
                .user(userRepository.findByUserId(requestDto.getUser()))
                .ent(repository.findByEntId(requestDto.getEnt()))
                .title(requestDto.getTitle())
                .contnet(requestDto.getContent())
                .fileName(requestDto.getFileName())
                .isNotice(requestDto.isNotice())
                .build();
        try {
            feedRepository.save(feed);

            if(feed.getFeedId() == null){
                throw new RuntimeException("Failed to save feed."); // 예외를 던지면 트랜잭션 롤백됨
            }
        } catch (Exception e) {
            // 예외 처리
            return false;
        }
        return true;
    }
}

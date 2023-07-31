package com.ssafy.singstreet.ent.service;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntFeed;
import com.ssafy.singstreet.ent.db.entity.EntLike;
import com.ssafy.singstreet.ent.db.entity.EntLikeId;
import com.ssafy.singstreet.ent.db.repo.EntFeedRepository;
import com.ssafy.singstreet.ent.db.repo.EntLikeRepository;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.ent.model.entFeedDto.EntFeedLikeRequestDto;
import com.ssafy.singstreet.ent.model.entFeedDto.EntFeedResponseDto;
import com.ssafy.singstreet.ent.model.entFeedDto.EntFeedCreateRequestDto;
import com.ssafy.singstreet.ent.model.entFeedDto.EntFeedUpdateRequestDto;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EntFeedService {
    private final EntRepository repository;
    private final UserRepository userRepository;
    private final EntFeedRepository feedRepository;
    private final EntLikeRepository likeRepository;


    // feed Read -------------------------------
    public Slice<EntFeedResponseDto> readAll(int entId, int page, int size){
        Ent ent = repository.findById(entId).orElseThrow(()->
                new IllegalArgumentException("해당 엔터가 없습니다. id" + entId));

        Slice<EntFeed> feedList = feedRepository.findByEnt(ent, PageRequest.of(page,size));
        return feedList.map(this::convertFeedToDto);
    }

    public Slice<EntFeedResponseDto> readNotice(int entId, int page, int size){
        Ent ent = repository.findById(entId).orElseThrow(()->
                new IllegalArgumentException("해당 엔터가 없습니다. id" + entId));

        Slice<EntFeed> feedList = feedRepository.findByEntAndIsNotice(ent, PageRequest.of(page,size));
        return feedList.map(this::convertFeedToDto);
    }

    public Slice<EntFeedResponseDto> readCommon(int entId, int page, int size){
        Ent ent = repository.findById(entId).orElseThrow(()->
                new IllegalArgumentException("해당 엔터가 없습니다. id" + entId));

        Slice<EntFeed> feedList = feedRepository.findByEntAndIsCommon(ent, PageRequest.of(page,size));
        return feedList.map(this::convertFeedToDto);
    }



    // feed Save -------------------------------
    @Transactional
    public boolean saveFeed(EntFeedCreateRequestDto requestDto){
        EntFeed feed = EntFeed.builder()
                .user(userRepository.findByUserId(requestDto.getUser()))
                .ent(repository.findByEntId(requestDto.getEnt()))
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
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


    // feed Update ------------------------------
    @Transactional
    public boolean updateFeed(EntFeedUpdateRequestDto requestDto){
        EntFeed feed = feedRepository.findById(requestDto.getFeedId())
                .orElseThrow(() -> new IllegalArgumentException("해당 피드가 없습니다. feedId : " + requestDto.getFeedId()));

        feed.update(requestDto.getTitle(), requestDto.getContent());
        feed.updateFileName(requestDto.getFileName());

        feedRepository.save(feed);

        if (requestDto.getFileName() != null){
            feed = EntFeed.builder()
                    .fileName(requestDto.getFileName())
                    .build();
        }
        feedRepository.save(feed);
        return true;
    }




    // feed Like ---------------------------------------
    public String like(EntFeedLikeRequestDto requestDto){
        EntLikeId likeId = EntLikeId.builder()
                .user(userRepository.findByUserId(requestDto.getUserId()))
                .feed(feedRepository.findByFeedId(requestDto.getFeedId()))
                .build();

        Optional<EntLike> likeOptional  = likeRepository.findById(likeId);

        if(likeOptional.isPresent()){   //존재여부 확인
            EntLike like = likeOptional.get();  //like객체 가져오기
            likeRepository.delete(like);
            return "delete";
        }else{
            EntLike like = new EntLike(likeId);
            likeRepository.save(like);
            return "save";
        }
    }



    // convert ------------------------------------------
    public EntFeedResponseDto convertFeedToDto(EntFeed feed){
        return EntFeedResponseDto.builder()
                .feedId(feed.getFeedId())
                .userId(feed.getUser().getUserId())
                .entId(feed.getEnt().getEntId())
                .content(feed.getContent())
                .filename(feed.getFileName())
                .isNotice(feed.getIsNotice())
                .title(feed.getTitle())
                .build();
    }

}

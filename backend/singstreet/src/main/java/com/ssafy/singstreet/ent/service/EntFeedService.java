package com.ssafy.singstreet.ent.service;

import com.ssafy.singstreet.ent.db.entity.*;
import com.ssafy.singstreet.ent.db.repo.EntFeedCommentRepository;
import com.ssafy.singstreet.ent.db.repo.EntFeedRepository;
import com.ssafy.singstreet.ent.db.repo.EntLikeRepository;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.ent.model.entFeedCommentDto.EntFeedCommentRequestDto;
import com.ssafy.singstreet.ent.model.entFeedCommentDto.EntFeedCommentResponseDto;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EntFeedService {
    private final EntRepository repository;
    private final UserRepository userRepository;
    private final EntFeedRepository feedRepository;
    private final EntLikeRepository likeRepository;
    private final EntFeedCommentRepository commentRepository;


    // Feed=========================================================
    // Feed Read
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

    // feed Save
    @Transactional
    public boolean saveFeed(EntFeedCreateRequestDto requestDto){
        EntFeed feed = EntFeed.builder()
                .user(userRepository.findByUserId(requestDto.getUser()))
                .ent(repository.findByEntIdAndIsDeleted(requestDto.getEnt(),false))
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

    // feed Update
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

    // feed Delete ------------------------------ 고치고싶다....
    @Transactional
    public boolean delete(int feedId){
        EntFeed feed = feedRepository.findByFeedId(feedId);
//        likeRepository.deleteAllByIdEntFeed(feed);

        List<EntFeedComment> comments = commentRepository.findEntFeedCommentByFeed(feed);
        commentRepository.deleteAll(comments);

        List<EntLike> entLikes = likeRepository.findAllByIdFeed(feed);
        likeRepository.deleteAll(entLikes);

        feedRepository.delete(feed);
        return true;
    }



    // Like ---------------------------------------------------------
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



    // Comment ------------------------------------------------------
    // Comment Read
    public List<EntFeedCommentResponseDto> readComment(int feedId){
        EntFeed feed = feedRepository.findByFeedId(feedId);
        List<EntFeedComment> commentList = commentRepository.findEntFeedCommentByFeed(feed);
        return commentList.stream().map(this::convertCommentToDto).collect(Collectors.toList());
    }

    // Comment Save
    @Transactional
    public boolean saveComment(EntFeedCommentRequestDto requestDto){
        EntFeedComment comment = EntFeedComment.builder()
                .feed(feedRepository.findByFeedId(requestDto.getFeedId()))
                .user(userRepository.findByUserId(requestDto.getUserId()))
                .content(requestDto.getContent())
                .build();

        commentRepository.save(comment);

        return true;
    }

    // Comment Delete
    @Transactional
    public boolean deleteComment(int commentId){
        commentRepository.deleteById(commentId);
        return true;
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
    public EntFeedCommentResponseDto convertCommentToDto(EntFeedComment comment){
        return EntFeedCommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .userId(comment.getUser().getUserId())
                .nickname(comment.getUser().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

}

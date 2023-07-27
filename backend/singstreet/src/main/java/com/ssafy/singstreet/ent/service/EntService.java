package com.ssafy.singstreet.ent.service;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntTag;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.ent.db.repo.EntTagRepository;
import com.ssafy.singstreet.ent.model.EntSaveRequestDto;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EntService {
    private final EntRepository repository;
    private final EntTagRepository tagRepository;
    private final UserRepository userRepository;


    public void saveTagList(String[] tagList, Ent entId){// tag 생성
        for (String tag : tagList) {
            tagRepository.save(EntTag
                    .builder()
                    .entId(entId)
                    .tagName(tag)
                    .build());
        }
    }

    @Transactional
    public int save(EntSaveRequestDto requestDto, int userId){
        Ent ent = Ent.builder()
                .user(userRepository.findByUserId(userId))
                .entName(requestDto.getEntName())
                .isAutoAccepted(requestDto.getIsAutoAccepted())
                .entInfo(requestDto.getEntInfo())
                .entImg(requestDto.getEntImg())
                .build();

        repository.save(ent);

        Ent entId = repository.findByEntId(ent.getEntId());
        String[] tagList = requestDto.getEntTagList().split("\\s*#\\s*");

        saveTagList(tagList, entId);
        return userId;
    }

    @Transactional
    public void update(EntSaveRequestDto requestDto, int requestEntId){
        Ent ent = repository.findById(requestEntId).orElseThrow(()->
                new IllegalArgumentException("해당 엔터 없습니다. id=" + requestEntId));

        ent.update(requestDto.getEntName(),requestDto.getIsAutoAccepted(),requestDto.getEntInfo(),requestDto.getEntImg());

        if(!requestDto.getEntTagList().isEmpty()){
            Ent entId = repository.findByEntId(requestEntId);

            tagRepository.deleteByEntId(entId);

            String[] tagList = requestDto.getEntTagList().split("\\s*#\\s*");
            saveTagList(tagList, entId);
        }
    }
}

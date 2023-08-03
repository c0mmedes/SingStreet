package com.ssafy.singstreet.ent.service;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntMember;
import com.ssafy.singstreet.ent.db.entity.EntTag;
import com.ssafy.singstreet.ent.db.repo.EntMemberRepository;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.ent.db.repo.EntTagRepository;
import com.ssafy.singstreet.ent.model.entDto.*;
import com.ssafy.singstreet.user.db.entity.User;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EntService {
    private final EntRepository repository;
    private final EntTagRepository tagRepository;
    private final UserRepository userRepository;
    private final EntMemberRepository memberRepository;

    public EntPageListResponseDto read(int page, int size){
        Slice<Ent> entSlice = repository.findByIsDeleted(false ,PageRequest.of(page,size, Sort.Direction.ASC, "entId"));
        Slice<EntResponseDto> entSliceList = entSlice.map(this::convertEntToDto);

        List<Ent> entIdList = entSlice.getContent().stream().distinct().collect(Collectors.toList());
        List<EntTag> tags = tagRepository.findByEntIdList(entIdList);
        List<EntTagResponseDto> tagList = tags.stream().map(this::convertTagToDto).collect(Collectors.toList());

        return new EntPageListResponseDto(entSliceList,tagList);
    }
    public EntDetailResponseDto readDetail(int entId){
        Ent ent = repository.findByEntId(entId);
        EntResponseDto entResponseDto = convertEntToDto(ent);
        List<EntTag> tags = tagRepository.findAllByEntId(ent);
        List<EntTagResponseDto> tagList = tags.stream().map(this::convertTagToDto).collect(Collectors.toList());

        return new EntDetailResponseDto(entResponseDto, tagList);
    }
    public EntListResponseDto readMyEnt(int userId){
        User user = userRepository.findByUserId(userId);
        List<Ent> entList = repository.findByUserAndIsDeleted(user, false);

        List<EntResponseDto> entResponseDtos = entList.stream().map(this::convertEntToDto).collect(Collectors.toList());
        List<EntTag> tags = tagRepository.findByEntIdList(entList);
        List<EntTagResponseDto> tagList = tags.stream().map(this::convertTagToDto).collect(Collectors.toList());

        return new EntListResponseDto(entResponseDtos, tagList);
    }

    @Transactional
    public boolean create(EntSaveRequestDto requestDto, int userId){
        Ent ent = Ent.builder()
                .user(userRepository.findByUserId(userId))
                .entName(requestDto.getEntName())
                .isAutoAccepted(requestDto.getIsAutoAccepted())
                .entInfo(requestDto.getEntInfo())
                .entImg(requestDto.getEntImg())
                .build();
        repository.save(ent);


        EntMember entMember = EntMember.builder()
                .ent(ent)
                .user(ent.getUser())
                .isLeader(true)
                .build();
        memberRepository.save(entMember);


        if(requestDto.getEntTagList() != null) {
            Ent entId = repository.findByEntId(ent.getEntId());
            String[] tagList = requestDto.getEntTagList().split("\\s*#\\s*");

            saveTagList(tagList, entId);
        }
        return true;
    }

    @Transactional
    public boolean update(EntSaveRequestDto requestDto, int requestEntId){
        Ent ent = repository.findById(requestEntId).orElseThrow(()->
                new IllegalArgumentException("해당 엔터 없습니다. id=" + requestEntId));

        ent.update(requestDto.getEntName(),requestDto.getIsAutoAccepted(),requestDto.getEntInfo(),requestDto.getEntImg());

        if(requestDto.getEntTagList() != null){
            List<EntTag> currentTagList = tagRepository.findAllByEntId(ent);

            tagRepository.deleteAll(currentTagList);

            String[] newtagList = requestDto.getEntTagList().split("\\s*#\\s*");
            saveTagList(newtagList, ent);
        }
        return true;
    }

    @Transactional
    public boolean delete(int requestEntId){
        Ent ent = repository.findById(requestEntId).orElseThrow(()->
                new IllegalArgumentException("해당 엔터 없습니다. id=" + requestEntId));

        ent.delete();
        if(ent.getIsDeleted() == true){
            return true;
        }else
            return false;
    }







    public void saveTagList(String[] tagList, Ent ent){// tag 생성
        for(int i=1; i<tagList.length; i++){
            String tag = tagList[i];
            tagRepository.save(EntTag
                    .builder()
                    .entId(ent)
                    .tagName(tag)
                    .build());
        }
    }






    // Convert ---------------------------------------------
    public EntTagResponseDto convertTagToDto(EntTag tag){
        return EntTagResponseDto.builder()
                .entTagId(tag.getEntTagId())
                .entId(tag.getEntId().getEntId())
                .tagName(tag.getTagName())
                .build();
    }

    public EntResponseDto convertEntToDto(Ent ent){
        return EntResponseDto.builder()
                .entId(ent.getEntId())
                .entName(ent.getEntName())
                .entImg(ent.getEntImg())
                .entInfo(ent.getEntInfo())
                .isAutoAccepted(ent.getIsAutoAccepted())
                .build();
    }


}

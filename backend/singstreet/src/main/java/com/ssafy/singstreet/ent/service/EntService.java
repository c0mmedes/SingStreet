package com.ssafy.singstreet.ent.service;

import com.ssafy.singstreet.config.AmazonS3Service;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EntService {
    private final EntRepository repository;
    private final EntTagRepository tagRepository;
    private final UserRepository userRepository;
    private final EntMemberRepository memberRepository;
    private final AmazonS3Service amazonS3Service;

    // 엔터 전체 목록 조회
    public Slice<EntResponseDto> read(int page, int size){
        Slice<Ent> entSlice = repository.findByIsDeleted(false ,PageRequest.of(page,size, Sort.Direction.ASC, "entId"));
        Slice<EntResponseDto> entSliceList = entSlice.map(this::convertEntToDto);

        for (EntResponseDto ent : entSliceList){
            List<EntTag> tagList = tagRepository.findAllByEntId(repository.findByEntIdAndIsDeleted(ent.getEntId(),false));
            List<String> tagNameList = tagList.stream().map(this::convertTagToName).collect(Collectors.toList());
            ent.update(tagNameList);
        }
        return entSliceList;
    }
    
    //엔터 상세 조회
    public EntResponseDto readDetail(int entId){
        Ent ent = repository.findByEntIdAndIsDeleted(entId,false);
        EntResponseDto entResponseDto = convertEntToDto(ent);
        List<EntTag> tagList = tagRepository.findAllByEntId(ent);
        List<String> tagNameList = tagList.stream().map(this::convertTagToName).collect(Collectors.toList());

        entResponseDto.update(tagNameList);
        return entResponseDto;
    }
    
    // 내 엔터 목록 조회
    public List<EntResponseDto> readMyEnt(int userId){
        // userId로 찾은 EntMember들
        List<EntMember> memberList = memberRepository.findAllByUserAndIsDeleted(userRepository.findByUserId(userId),false);
        List<Ent> entList = new ArrayList<>();
        for(EntMember member : memberList){
            entList.add(member.getEnt());
        }
        List<EntResponseDto> entResponseDtos = entList.stream().map(this::convertEntToDto).collect(Collectors.toList());

        for (EntResponseDto ent : entResponseDtos){
            List<EntTag> tagList = tagRepository.findAllByEntId(repository.findByEntIdAndIsDeleted(ent.getEntId(),false));
            List<String> tagNameList = tagList.stream().map(this::convertTagToName).collect(Collectors.toList());
            ent.update(tagNameList);
        }
        return entResponseDtos;
    }

    public boolean entNameCheck(String entName){
        Ent ent = repository.findByEntName(entName);
        if (ent == null){
            return true;
        }
        return false;
    }

    // 엔터 생성
    @Transactional
    public boolean create(EntSaveRequestDto requestDto, int userId, MultipartFile file){
        // 중복체크
        String entName = requestDto.getEntName();
        if(!entNameCheck(entName)) return false;

        String s3Url = "";

        if (file != null) {
            s3Url = amazonS3Service.uploadFile(file);
        }

        Ent ent = Ent.builder()
                .user(userRepository.findByUserId(userId))
                .entName(requestDto.getEntName())
                .isAutoAccepted(requestDto.getIsAutoAccepted())
                .entInfo(requestDto.getEntInfo())
                .entImg(s3Url)
                .build();
        repository.save(ent);

        if(requestDto.getEntImg() != null){
            ent.updateImg(requestDto.getEntImg());
        }


        EntMember entMember = EntMember.builder()
                .ent(ent)
                .user(ent.getUser())
                .isLeader(true)
                .build();
        memberRepository.save(entMember);


        if(requestDto.getEntTagList() != null) {
            Ent entId = repository.findByEntIdAndIsDeleted(ent.getEntId(),false);
            String[] tagList = requestDto.getEntTagList().split("\\s*#\\s*");

            saveTagList(tagList, entId);
        }
        return true;
    }

    // 엔터 수정
    @Transactional
    public boolean update(EntSaveRequestDto requestDto, int requestEntId, MultipartFile file){
        Ent ent = repository.findById(requestEntId).orElseThrow(()->
                new IllegalArgumentException("해당 엔터 없습니다. id=" + requestEntId));

        String s3Url = "";

        if (file == null) {
            s3Url = ent.getEntImg();
        } else {
            s3Url = amazonS3Service.uploadFile(file);
        }


        ent.update(requestDto.getEntName(),requestDto.getIsAutoAccepted(),requestDto.getEntInfo(),s3Url);

        if(requestDto.getEntTagList() != null){
            List<EntTag> currentTagList = tagRepository.findAllByEntId(ent);

            tagRepository.deleteAll(currentTagList);

            String[] newtagList = requestDto.getEntTagList().split("\\s*#\\s*");
            saveTagList(newtagList, ent);
        }
        return true;
    }

    // 엔터 삭제
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
                .userId(ent.getUser().getUserId())
                .entName(ent.getEntName())
                .entImg(ent.getEntImg())
                .entInfo(ent.getEntInfo())
                .isAutoAccepted(ent.getIsAutoAccepted())
                .build();
    }
    public String convertTagToName(EntTag tag){
        return tag.getTagName();
    }


}

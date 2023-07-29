package com.ssafy.singstreet.ent.service;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntMember;
import com.ssafy.singstreet.ent.db.entity.EntTag;
import com.ssafy.singstreet.ent.db.repo.EntMemberRepository;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.ent.db.repo.EntTagRepository;
import com.ssafy.singstreet.ent.model.entDto.EntDetailResponseDto;
import com.ssafy.singstreet.ent.model.entDto.EntPageListResponseDto;
import com.ssafy.singstreet.ent.model.entDto.EntResponseDto;
import com.ssafy.singstreet.ent.model.entDto.EntSaveRequestDto;
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
        //위의 결과에서 EntId만 뽑아 List를 만들꺼임 -> Stream API사용
        //List<Integer> entList = entSlice.getContent().stream().map(Ent::getEntId).distinct().collect(Collectors.toList());

        List<Ent> entIdList = entSlice.getContent().stream().distinct().collect(Collectors.toList());
        List<EntTag> tagList = tagRepository.findByEntIdList(entIdList);
        return new EntPageListResponseDto(entSlice,tagList);
    }
    public EntDetailResponseDto readDetail(int entId){
        Ent ent = repository.findByEntId(entId);
        List<EntTag> tagList = tagRepository.findAllByEntId(ent);

        return new EntDetailResponseDto(ent, tagList);
    }
    public EntResponseDto readMyEnt(int userId){
        User user = userRepository.findByUserId(userId);
        List<Ent> entList = repository.findByUser(user);
        List<EntTag> tagList = tagRepository.findByEntIdList(entList);

        return new EntResponseDto(entList, tagList);
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
        return userId;
    }



    @Transactional
    public void update(EntSaveRequestDto requestDto, int requestEntId){
        Ent ent = repository.findById(requestEntId).orElseThrow(()->
                new IllegalArgumentException("해당 엔터 없습니다. id=" + requestEntId));

        ent.update(requestDto.getEntName(),requestDto.getIsAutoAccepted(),requestDto.getEntInfo(),requestDto.getEntImg());

        if(requestDto.getEntTagList() != null){
            Ent entId = repository.findByEntId(requestEntId);
            List<EntTag> currentTagList = tagRepository.findAllByEntId(entId);
//-------------------코드 수정필요------------------------------ 너무 비효율적임
            for(EntTag tag:currentTagList){
                tagRepository.delete(tag);
            }
//------------------------------------------------------------
            String[] newtagList = requestDto.getEntTagList().split("\\s*#\\s*");
            saveTagList(newtagList, entId);
        }
    }


    @Transactional
    public boolean delete(int requestEntId){
        Ent ent = repository.findById(requestEntId).orElseThrow(()->
                new IllegalArgumentException("해당 엔터 없습니다. id=" + requestEntId));

        ent.delete();
        if(ent.isDeleted() == true){
            return true;
        }else
            return false;
    }

    public void saveTagList(String[] tagList, Ent entId){// tag 생성
        for (String tag : tagList) {
            tagRepository.save(EntTag
                    .builder()
                    .entId(entId)
                    .tagName(tag)
                    .build());
        }
    }
}

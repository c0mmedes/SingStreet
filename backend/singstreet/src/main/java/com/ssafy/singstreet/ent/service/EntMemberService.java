package com.ssafy.singstreet.ent.service;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntApplicant;
import com.ssafy.singstreet.ent.db.repo.EntApplicantRepository;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.ent.model.entMemberDto.EntApplyRequestDto;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EntMemberService {
    private final EntApplicantRepository entApplicantRepository;
    private final UserRepository userRepository;
    private final EntRepository entRepository;

    public List<EntApplicant> readAppl(int requestEntId){
        Ent entId = entRepository.findByEntId(requestEntId);

        return entApplicantRepository.findEntApplicantsByEntId(entId);
    }

    public boolean save(EntApplyRequestDto requestDto){
        EntApplicant entApplicant = EntApplicant.builder()
                .entId(entRepository.findByEntId(requestDto.getEntId()))
                .userId(userRepository.findByUserId(requestDto.getUserId()))
                .hope(requestDto.getHope())
                .artist(requestDto.getArtist())
                .age(requestDto.getAge())
                .content(requestDto.getContent())
                .build();
        if (requestDto.getAudioName() != null){
            entApplicant.builder().audioName(requestDto.getAudioName()).build();
        }

        entApplicantRepository.save(entApplicant);

        EntApplicant id = entApplicantRepository.findEntApplicantByApplId(entApplicant.getApplId());

        if (id == null){
            return false;
        }
        return true;
    }
}

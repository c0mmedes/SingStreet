package com.ssafy.singstreet.ent.service;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntApplicant;
import com.ssafy.singstreet.ent.db.entity.EntMember;
import com.ssafy.singstreet.ent.db.entity.EntMemberId;
import com.ssafy.singstreet.ent.db.repo.EntApplicantRepository;
import com.ssafy.singstreet.ent.db.repo.EntMemberRepository;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.ent.model.entMemberDto.EntApplyRequestDto;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EntMemberService {
    private final EntApplicantRepository applicantRepository;
    private final UserRepository userRepository;
    private final EntRepository repository;
    private final EntMemberRepository memberRepository;

    public List<EntApplicant> readAppl(int requestEntId){
        Ent entId = repository.findByEntId(requestEntId);

        return applicantRepository.findEntApplicantsByEntId(entId);
    }

    public boolean saveAppl(EntApplyRequestDto requestDto){
        EntApplicant entApplicant = EntApplicant.builder()
                .entId(repository.findByEntId(requestDto.getEntId()))
                .userId(userRepository.findByUserId(requestDto.getUserId()))
                .hope(requestDto.getHope())
                .artist(requestDto.getArtist())
                .age(requestDto.getAge())
                .content(requestDto.getContent())
                .build();
        if (requestDto.getAudioName() != null){
            entApplicant.builder().audioName(requestDto.getAudioName()).build();
        }

        applicantRepository.save(entApplicant);

        EntApplicant id = applicantRepository.findEntApplicantByApplId(entApplicant.getApplId());

        if (id == null){
            return false;
        }
        return true;
    }

    public boolean saveMember(int applId){
        EntApplicant entApplicant = applicantRepository.findEntApplicantByApplId(applId);
        entApplicant.accept();
        applicantRepository.save(entApplicant);
        if (entApplicant.getIsAccepted() != true)
            return false;

        EntMember entMember = EntMember.builder()
                .ent(entApplicant.getEntId())
                .user(entApplicant.getUserId())
                .build();
        memberRepository.save(entMember);
        if(entMember.getMemberId() == null)
            return false;

        return true;
    }
}

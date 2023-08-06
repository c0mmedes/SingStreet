package com.ssafy.singstreet.ent.service;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntApplicant;
import com.ssafy.singstreet.ent.db.entity.EntMember;
import com.ssafy.singstreet.ent.db.entity.EntMemberId;
import com.ssafy.singstreet.ent.db.repo.EntApplicantRepository;
import com.ssafy.singstreet.ent.db.repo.EntMemberRepository;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.ent.model.entMemberDto.EntApplyDetailResponseDto;
import com.ssafy.singstreet.ent.model.entMemberDto.EntApplyRequestDto;
import com.ssafy.singstreet.ent.model.entMemberDto.EntApplyResponseDto;
import com.ssafy.singstreet.ent.model.entMemberDto.EntMemberResponseDto;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EntMemberService {
    private final EntApplicantRepository applicantRepository;
    private final UserRepository userRepository;
    private final EntRepository repository;
    private final EntMemberRepository memberRepository;

    // 지원자 ---------------------------------------------------------------
    //지원자 목록
    public List<EntApplyResponseDto> readAppl(int requestEntId){
        Ent entId = repository.findByEntIdAndIsDeleted(requestEntId,false);
        List<EntApplicant> applyList = applicantRepository.findEntApplicantsByEntIdAndIsConfirmed(entId, false);

        return applyList.stream().map(this::convertApplyToDto).collect(Collectors.toList());
    }

    // 지원자 상세
    public EntApplyDetailResponseDto readApplDetail(int applId){
        EntApplicant apply = applicantRepository.findEntApplicantByApplId(applId);

        return convertApplyDetailToDto(apply);
    }

    // 지원자 등록
    public boolean saveAppl(EntApplyRequestDto requestDto){
        EntApplicant done = applicantRepository.findEntApplicantByEntIdAndUserId(repository.findByEntIdAndIsDeleted(requestDto.getEntId(),false),userRepository.findByUserId(requestDto.getUserId()));
        if(done != null)
            return false;

        EntApplicant entApplicant = EntApplicant.builder()
                .entId(repository.findByEntIdAndIsDeleted(requestDto.getEntId(),false))
                .userId(userRepository.findByUserId(requestDto.getUserId()))
                .hope(requestDto.getHope())
                .artist(requestDto.getArtist())
                .content(requestDto.getContent())
                .build();
        if (requestDto.getAudioName() != null){
            entApplicant.updateAudioName(requestDto.getAudioName());
        }

        applicantRepository.save(entApplicant);

        EntApplicant id = applicantRepository.findEntApplicantByApplId(entApplicant.getApplId());

        if (id == null){
            return false;
        }
        return true;
    }

    // 멤버 -----------------------------------------------------------------
    // 멤버 목록 조회
    public
    List<EntMemberResponseDto> readMember(int entId){
        Ent ent = repository.findByEntIdAndIsDeleted(entId,false);
        List<EntMember> memberList = memberRepository.findAllByEnt(ent);

        return memberList.stream().map(this::convertMemberToDto).collect(Collectors.toList());
    }
    
    // 멤버 승인 / 거절
    @Transactional
    public boolean accepteMember(int applId,boolean isAccepted){
        EntApplicant entApplicant = applicantRepository.findEntApplicantByApplId(applId);
        if(entApplicant.getIsConfirmed() == true)
            return false;
        if (isAccepted){
            entApplicant.accept();
            applicantRepository.save(entApplicant);
        }else{
            entApplicant.reject();
            applicantRepository.save(entApplicant);
            return true;
        }

        EntMember entMember = EntMember.builder()
                .ent(entApplicant.getEntId())
                .user(entApplicant.getUserId())
                .build();
        memberRepository.save(entMember);
        if(entMember.getMemberId() == null)
            return false;

        return true;
    }
    
    // 멤버 삭제
    @Transactional
    public boolean deleteMember(int memberId){
        EntMember member = memberRepository.findByMemberId(memberId);
        if (member.getIsLeader() == true)
            return false;
        member.delete();
        memberRepository.save(member);
        if(member.getIsDeleted() != true)
            return false;
        return true;
    }



    // Convert -----------------------------
    public EntApplyResponseDto convertApplyToDto(EntApplicant apply){
        return EntApplyResponseDto.builder()
                .userId(apply.getUserId().getUserId())
                .applId(apply.getApplId())
                .nickname(apply.getUserId().getNickname())
                .createAt(apply.getCreatedAt())
                .build();
    }
    public EntApplyDetailResponseDto convertApplyDetailToDto(EntApplicant apply){
        EntApplyDetailResponseDto result = EntApplyDetailResponseDto.builder()
                .applId(apply.getApplId())
                .entId(apply.getEntId().getEntId())
                .userId(apply.getUserId().getUserId())
                .nickname(apply.getUserId().getNickname())
                .hope(apply.getHope())
                .artist(apply.getArtist())
                .content(apply.getContent())
                .audioName(apply.getAudioName())
                .build();
        if(apply.getAudioName() != null){
            apply.updateAudioName(result.getAudioName());
        }
        return result;
    }

    public EntMemberResponseDto convertMemberToDto(EntMember member){
        return EntMemberResponseDto.builder()
                .userId(member.getUser().getUserId())
                .email(member.getUser().getEmail())
                .createdAt(member.getCreatedAt())
                .gender(member.getUser().getGender())
                .nickname(member.getUser().getNickname())
                .build();
    }

}

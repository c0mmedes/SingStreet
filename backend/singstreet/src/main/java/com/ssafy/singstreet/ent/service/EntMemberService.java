package com.ssafy.singstreet.ent.service;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntApplicant;
import com.ssafy.singstreet.ent.db.entity.EntMember;
import com.ssafy.singstreet.ent.db.entity.EntMemberId;
import com.ssafy.singstreet.ent.db.repo.EntApplicantRepository;
import com.ssafy.singstreet.ent.db.repo.EntMemberRepository;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.ent.model.entMemberDto.EntApplyRequestDto;
import com.ssafy.singstreet.ent.model.entMemberDto.EntApplyResponseDto;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<EntApplyResponseDto> readAppl(int requestEntId){
        Ent entId = repository.findByEntId(requestEntId);
        List<EntApplicant> applyList = applicantRepository.findEntApplicantsByEntIdAndIsConfirmed(entId, false);

        return applyList.stream().map(this::convertApplyToDto).collect(Collectors.toList());
    }
    public boolean saveAppl(EntApplyRequestDto requestDto){
        EntApplicant done = applicantRepository.findEntApplicantByEntIdAndUserId(repository.findByEntId(requestDto.getEntId()),userRepository.findByUserId(requestDto.getUserId()));
        if(done != null)
            return false;

        EntApplicant entApplicant = EntApplicant.builder()
                .entId(repository.findByEntId(requestDto.getEntId()))
                .userId(userRepository.findByUserId(requestDto.getUserId()))
                .hope(requestDto.getHope())
                .artist(requestDto.getArtist())
                .age(requestDto.getAge())
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
    public List<EntMember> readMember(int entId){
        Ent ent = repository.findByEntId(entId);
        List<EntMember> memberList = memberRepository.findAllByEnt(ent);

        return memberList;
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
                .nickname(apply.getUserId().getNickname())
                .createAt(apply.getCreatedAt())
                .build();
    }

}

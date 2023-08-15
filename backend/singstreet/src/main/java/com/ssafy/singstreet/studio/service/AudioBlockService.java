package com.ssafy.singstreet.studio.service;

import com.ssafy.singstreet.config.AmazonS3Service;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.repo.ProjectRepository;
import com.ssafy.singstreet.project.service.ProjectService;
import com.ssafy.singstreet.studio.db.entity.AudioBlock;
import com.ssafy.singstreet.studio.db.repo.AudioBlockRepository;
import com.ssafy.singstreet.studio.model.*;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import com.ssafy.singstreet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AudioBlockService {
    private final AudioBlockRepository audioBlockRepository;
    private final ProjectRepository projectRepository;
    private final AmazonS3Service amazonS3Service;
    private final UserRepository userRepository;


    public List<AudioBlockResponseDTO> getBlocksByProjectId(int projectId){
        System.out.println("ji");
        Project project = projectRepository.findByProjectId(projectId);
        List<AudioBlock> audioBlockList = audioBlockRepository.findByProject_ProjectIdAndIsDeletedFalse(project);

        List<AudioBlockResponseDTO> audioBlockResponseDTOList = audioBlockList.stream().map(this::convertAudioToDto).collect(Collectors.toList());

        return audioBlockResponseDTOList;
    }


    public Boolean create(AudioBlockRequestDTO requestDTO, int userId, MultipartFile file){
        System.out.println(requestDTO);
        System.out.println(file);

        String s3Url = "";

        if (file == null) {
            System.out.println("false");
            return false;
        }else{
            s3Url = amazonS3Service.uploadFile(file);

        }
        System.out.println(requestDTO.projectId);
        AudioBlock audioBlock = AudioBlock.builder()
                .project(projectRepository.findByProjectId(requestDTO.getProjectId()))
                .user(userRepository.findByUserId(userId))
                .testId(requestDTO.getTestId())
                .left(requestDTO.getLeft())
                .top(requestDTO.getTop())
                .fileLocation(s3Url)
                .blockName(requestDTO.blockName)
                .build();
        audioBlockRepository.save(audioBlock);

        return true;
    }

    public Boolean deleteBlock(int blockId){
        AudioBlock audioBlock = audioBlockRepository.findByBlockId(blockId);
        audioBlock.delete();
        return true;
    }

    public AudioBlockResponseDTO convertAudioToDto(AudioBlock audioBlock){
        return AudioBlockResponseDTO.builder()
                .blockId(audioBlock.getBlockId())
                .userId(audioBlock.getUser().getUserId())
                .nickname(audioBlock.getUser().getNickname())
                .testId(audioBlock.getTestId())
                .projectId(audioBlock.getProject().getProjectId())
                .left(audioBlock.getLeft())
                .top(audioBlock.getTop())
                .blockName(audioBlock.getBlockName())
                .file_location(audioBlock.getFileLocation())
                .build();
    }

    public void updateBlock(List<AudioBlockUpdateResponseDTO> blockList) {
        // blockId를 받아서 그 blockId에 해당하는 left, top을 업데이트
        for (int i = 0; i < blockList.size(); i++) {
            Integer blockId = blockList.get(i).getBlockId();
            AudioBlock audioBlock = audioBlockRepository.findByBlockId(blockId);
            audioBlock.updateBlock(blockList.get(i).getLeft(), blockList.get(i).getTop());
            audioBlockRepository.save(audioBlock);
        }
    }

    public void updateBlockName(AudioBlockNameUpdateResponseDTO dto) {
        // blockId를 받아서 그 blockId에 해당하는 blockName을 업데이트
        AudioBlock audioBlock = audioBlockRepository.findByBlockId(dto.getBlockId());
        audioBlock.updateBlock(dto.blockName);
        audioBlockRepository.save(audioBlock);
    }


    public List<AudioBlockResponseDTO> getAnotherBlock(AudioAnotherBlockRequestDto dto) {
        // dto.getCount 에서 갯수가져오고 project
        // projectId가 같으면서 delete가 false인것중에 전체에서 dto.getCount보다 뒤에있는 거
        List<AudioBlock> dtos = audioBlockRepository.findByProjectIdAndIsDeletedFalseWithOffset(dto.getProjectId(), dto.getNumber());
        List<AudioBlockResponseDTO> list = new ArrayList<>();

        for (int i = 0; i < dtos.size(); i++){
            list.add(convertAudioToDto(dtos.get(i)));
        }
        return list;
    }
}
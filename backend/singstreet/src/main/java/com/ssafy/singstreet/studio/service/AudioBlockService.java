package com.ssafy.singstreet.studio.service;

import com.ssafy.singstreet.config.AmazonS3Service;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.repo.ProjectRepository;
import com.ssafy.singstreet.project.service.ProjectService;
import com.ssafy.singstreet.studio.db.entity.AudioBlock;
import com.ssafy.singstreet.studio.db.repo.AudioBlockRepository;
import com.ssafy.singstreet.studio.model.AudioBlockRequestDTO;
import com.ssafy.singstreet.studio.model.AudioBlockResponseDTO;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import com.ssafy.singstreet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.RoundingMode;
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
        System.out.println(requestDTO);
        AudioBlock audioBlock = AudioBlock.builder()
                .project(projectRepository.findByProjectId(requestDTO.getProjectId()))
                .user(userRepository.findByUserId(userId))
                .testId(requestDTO.getTestId())
                .left(requestDTO.getLeft())
                .top(requestDTO.getTop())
                .fileLocation(s3Url)
                .build();
        audioBlockRepository.save(audioBlock);

        return true;
    }


//    public AudioBlock updateBlock(int id, AudioBlock updatedBlock) {
//        Optional<AudioBlock> optionalBlock = audioBlockRepository.findById(id);
//
//
//        return audioBlockRepository.save(existingBlock);
//    }

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
                .file_location(audioBlock.getFileLocation())
                .build();
    }
}

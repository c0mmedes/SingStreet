package com.ssafy.singstreet.studio.service;

import com.ssafy.singstreet.config.AmazonS3Service;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.repo.ProjectRepository;
import com.ssafy.singstreet.studio.db.entity.AudioBlock;
import com.ssafy.singstreet.studio.db.repo.AudioBlockRepository;
import com.ssafy.singstreet.studio.model.AudioBlockRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class AudioBlockService {
    private AudioBlockRepository audioBlockRepository;
    private ProjectRepository projectRepository;
    private AmazonS3Service amazonS3Service;
    public List<AudioBlock> getBlocksByProjectId(int projectId){
        return audioBlockRepository.findByProject_ProjectIdAndIsDeletedFalse(projectId);
    }
    public void addBlock(AudioBlockRequestDTO requestDTO, MultipartFile file){
        String s3url=amazonS3Service.uploadFile(file);
        AudioBlock audioBlock = null;
        audioBlock.setTestId(requestDTO.getTestId());
        audioBlock.setLeft(requestDTO.getLeft().setScale(3, RoundingMode.HALF_UP));
        audioBlock.setTop(requestDTO.getTop().setScale(3, RoundingMode.HALF_UP));
        Project project=projectRepository.findByProjectId(requestDTO.getProjectId());
        audioBlock.setProject(project);
        audioBlock.setNickname(requestDTO.getNickname());
        audioBlock.setFileLocation(s3url);
        audioBlockRepository.save(audioBlock);
    }


    public AudioBlock updateBlock(int id, AudioBlock updatedBlock) {
        Optional<AudioBlock> optionalBlock = audioBlockRepository.findById(id);

        if (!optionalBlock.isPresent()) {
            throw new RuntimeException("AudioBlock not found with id: " + id);
        }

        AudioBlock existingBlock = optionalBlock.get();

        // Update fields. You can optimize this by only updating fields that are changed
        existingBlock.setLeft(updatedBlock.getLeft());
        existingBlock.setTop(updatedBlock.getTop());
        existingBlock.setFileLocation(updatedBlock.getFileLocation());
        existingBlock.setNickname(updatedBlock.getNickname());
        existingBlock.setTestId(updatedBlock.getTestId());
        existingBlock.setFileLocation(updatedBlock.getFileLocation());
        // ... update other fields as necessary ...

        return audioBlockRepository.save(existingBlock);
    }

    public void deleteBlock(int id){
        AudioBlock ab=audioBlockRepository.findById(id).get();
        ab.setIsDeleted(true);
        audioBlockRepository.save(ab);
    }
}

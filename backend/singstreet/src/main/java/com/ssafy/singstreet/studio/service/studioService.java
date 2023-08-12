package com.ssafy.singstreet.studio.service;

import com.ssafy.singstreet.config.AmazonS3Service;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.repo.ProjectRepository;
import com.ssafy.singstreet.studio.db.entity.AudioBlock;
import com.ssafy.singstreet.studio.db.repo.AudioBlockRepository;
import com.ssafy.singstreet.studio.model.BlockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class studioService {
    private final AmazonS3Service amazonS3Service;
    private final ProjectRepository projectRepository;
    private final AudioBlockRepository audioBlockRepository;

    public void createBlock(BlockDto blockDto, MultipartFile file) {
        String s3Url = amazonS3Service.uploadFile(file);
        Project project = projectRepository.findByProjectId(blockDto.getProjectId());

        AudioBlock audioBlock = AudioBlock.builder()
                .project(project)
                .timeLine(blockDto.getTimeLine())
                .blockName(blockDto.getBlockName())
                .userId(blockDto.getUserId())
                .xPos(blockDto.getXPos())
                .yPos(blockDto.getYPos())
                .build();

        audioBlockRepository.save(audioBlock);
    }
}

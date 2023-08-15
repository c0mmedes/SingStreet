package com.ssafy.singstreet.studio.controller;

import com.ssafy.singstreet.studio.db.entity.AudioBlock;
import com.ssafy.singstreet.studio.model.*;
import com.ssafy.singstreet.studio.service.AudioBlockService;
import com.ssafy.singstreet.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/block")
public class AudioBlockController {
    private final AudioBlockService audioBlockService;
    private final UserService userService;

    @PostMapping("/add")
    @ApiOperation(value="블럭 추가하는 메서드입니다.")
    public ResponseEntity<Boolean> addBlock(
            @RequestPart(value="AudioBlockRequestDTO", required=false) AudioBlockRequestDTO requestDTO,
            @RequestPart(value = "file", required = false) MultipartFile file){
        int userId = userService.getCurrentUserId();
        System.out.println("userId: "+userId);
        System.out.println(requestDTO);

        return new ResponseEntity<>(audioBlockService.create(requestDTO, userId, file), HttpStatus.CREATED);
    }

    @GetMapping("/{project_id}")
    @ApiOperation(value="프로젝트 아이디로 블럭들 가져오기")
    public ResponseEntity<List<AudioBlockResponseDTO>> getBlocks(@PathVariable("project_id") int projectId){
        return new ResponseEntity<>(audioBlockService.getBlocksByProjectId(projectId),HttpStatus.OK);
    }

    @PutMapping("/update")
    @ApiOperation(value = "블럭 위치를 업데이트하기")
    public ResponseEntity<Void> updateBlock(@RequestBody List<AudioBlockUpdateResponseDTO> updatedBlock) {
        audioBlockService.updateBlock(updatedBlock);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/blockName/update")
    @ApiOperation(value = "블럭 이름을 업데이트하기")
    public ResponseEntity<Void> updateBlockName(@RequestBody AudioBlockNameUpdateResponseDTO updatedBlock) {
        audioBlockService.updateBlockName(updatedBlock);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/delete")
    public ResponseEntity<Boolean> deleteBlock(@RequestBody int blockId){
        return new ResponseEntity<>(audioBlockService.deleteBlock(blockId),HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "프로젝트 아이디와 현시점 블럭 카운트를 기준으로 남은 블럭들 가져오기")
    public ResponseEntity<List<AudioBlockResponseDTO>> getAnotherBlock(
            @RequestBody AudioAnotherBlockRequestDto dto) {
        return new ResponseEntity<>(audioBlockService.getAnotherBlock(dto),HttpStatus.OK);
    }

}

package com.ssafy.singstreet.studio.controller;

import com.ssafy.singstreet.studio.db.entity.AudioBlock;
import com.ssafy.singstreet.studio.model.AudioBlockRequestDTO;
import com.ssafy.singstreet.studio.model.AudioBlockResponseDTO;
import com.ssafy.singstreet.studio.service.AudioBlockService;
import com.ssafy.singstreet.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class AudioBlockController {
    private final AudioBlockService audioBlockService;
    private final UserService userService;

    @PostMapping("/block/add")
    @ApiOperation(value="블럭 추가하는 메서드입니다.")
    public ResponseEntity<Boolean> addBlock(
            @RequestPart(value="AudioBlockRequestDTO", required=false) AudioBlockRequestDTO requestDTO,
            @RequestPart(value = "file", required = false) MultipartFile file){
        int userId = userService.getCurrentUserId();
        System.out.println("userId: "+userId);
        System.out.println(requestDTO);

        return new ResponseEntity<>(audioBlockService.create(requestDTO, userId, file), HttpStatus.CREATED);
    }

    @GetMapping("/block/get/{project_id}")
    @ApiOperation(value="프로젝트 아이디로 블럭들 가져오기")
    public ResponseEntity<List<AudioBlockResponseDTO>> getBlocks(@PathVariable("project_id") int projectId){
        return new ResponseEntity<>(audioBlockService.getBlocksByProjectId(projectId),HttpStatus.OK);
    }

//    @PutMapping("/block/save")
//    @ApiOperation(value="블럭 위치를 저장하기")
//    public void updateBlock(@RequestBody List<AudioBlock> updatedBlock) {
//        for(int i=0; i<updatedBlock.size(); i++){
//            int id=updatedBlock.get(i).getBlockId();
//            audioBlockService.updateBlock(id, updatedBlock.get(i));
//        }
//    }

    @PutMapping("/block/delete")
    public ResponseEntity<Boolean> deleteBlock(@RequestBody int blockId){

        return new ResponseEntity<>(audioBlockService.deleteBlock(blockId),HttpStatus.OK);
    }
}

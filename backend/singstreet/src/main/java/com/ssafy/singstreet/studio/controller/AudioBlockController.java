package com.ssafy.singstreet.studio.controller;

import com.ssafy.singstreet.studio.db.entity.AudioBlock;
import com.ssafy.singstreet.studio.model.AudioBlockRequestDTO;
import com.ssafy.singstreet.studio.service.AudioBlockService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class AudioBlockController {
    AudioBlockService audioBlockService;

    @PostMapping("block/add")
    @ApiOperation(value="블럭 추가하는 메서드입니다.")
    public ResponseEntity<String> addBlock(@RequestPart(value="AudioBlockRequestDTO", required=false) AudioBlockRequestDTO requestDTO, @RequestPart(value = "file", required = false) MultipartFile file){
        try{
            audioBlockService.addBlock(requestDTO, file);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("에러났네요");
        }
        return ResponseEntity.ok("성공했으요");
    }

    @GetMapping("block/get/{project_id}")
    @ApiOperation(value="프로젝트 아이디로 블럭들 가져오기")
    public List<AudioBlock> getBlocks(@PathVariable("project_id") int project_id){
        return audioBlockService.getBlocksByProjectId(project_id);
    }

    @PutMapping("block/save")
    @ApiOperation(value="블럭 위치를 저장하기")
    public void updateBlock(@RequestBody List<AudioBlock> updatedBlock) {
        for(int i=0; i<updatedBlock.size(); i++){
            int id=updatedBlock.get(i).getBlockId();
            audioBlockService.updateBlock(id, updatedBlock.get(i));
        }
    }
}

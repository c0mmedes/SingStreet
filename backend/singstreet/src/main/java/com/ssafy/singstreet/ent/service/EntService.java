package com.ssafy.singstreet.ent.service;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntTag;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.ent.db.repo.EntTagRepository;
import com.ssafy.singstreet.ent.model.EntSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EntService {
    private final EntRepository repository;
    private final EntTagRepository tagRepository;

    @Transactional
    public void save(EntSaveRequestDto requestDto){
        Ent ent = requestDto.toEntEntity();
        repository.save(ent);

        String[] tagList = requestDto.getEntTagList().split("#");
        for(String tag : tagList){
            System.out.println(tag);
        }
        for (String tag : tagList) {
            tagRepository.save(EntTag
                    .builder()
                    .ent(ent)
                    .tagName(tag)
                    .build());
        }

    }
}

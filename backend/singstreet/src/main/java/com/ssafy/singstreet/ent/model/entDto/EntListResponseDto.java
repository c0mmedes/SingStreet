package com.ssafy.singstreet.ent.model.entDto;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntListResponseDto {
    private List<EntResponseDto> entList;
    private List<EntTagResponseDto> tagList;
}

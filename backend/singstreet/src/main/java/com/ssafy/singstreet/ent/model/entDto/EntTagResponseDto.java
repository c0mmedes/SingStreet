package com.ssafy.singstreet.ent.model.entDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntTagResponseDto {
    private int entTagId;
    private int entId;
    private String tagName;
}

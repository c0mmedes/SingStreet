package com.ssafy.singstreet.ent.model.entFeedDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntFeedLikeRequestDto {
    private int userId;
    private int feedId;
}

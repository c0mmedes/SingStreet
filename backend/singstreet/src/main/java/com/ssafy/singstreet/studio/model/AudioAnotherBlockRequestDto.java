package com.ssafy.singstreet.studio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AudioAnotherBlockRequestDto {
    public Integer projectId;
    public Integer number;
}

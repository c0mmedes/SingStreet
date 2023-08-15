package com.ssafy.singstreet.studio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AudioBlockNameUpdateResponseDTO {
    public Integer blockId;
    public String blockName;
}

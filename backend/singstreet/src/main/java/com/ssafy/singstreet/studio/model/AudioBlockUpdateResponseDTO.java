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
public class AudioBlockUpdateResponseDTO {
    public Integer blockId;
    public BigDecimal left;
    public BigDecimal top;
}

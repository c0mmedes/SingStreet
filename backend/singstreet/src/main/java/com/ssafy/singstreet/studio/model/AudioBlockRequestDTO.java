package com.ssafy.singstreet.studio.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AudioBlockRequestDTO {
    public int testId;
    public int projectId;
    public BigDecimal left;
    public BigDecimal top;
}

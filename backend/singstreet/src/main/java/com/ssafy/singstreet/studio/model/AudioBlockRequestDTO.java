package com.ssafy.singstreet.studio.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AudioBlockRequestDTO {
    public int blockId;
    public int testId;
    public int projectId;
    public BigDecimal left;
    public BigDecimal top;
    public String nickname;
}

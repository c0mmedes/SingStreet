package com.ssafy.singstreet.studio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
@Getter
@Builder
@AllArgsConstructor
public class AudioBlockResponseDTO {
    public int blockId;
    public int projectId;
    public int testId;
    public int userId;
    public String blockName;
    public BigDecimal left;
    public BigDecimal top;
    public String nickname;
    public String file_location;
}

package com.ssafy.singstreet.studio.model;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public class AudioBlockResponseDTO {
    public int block_id;
    public int project_id;
    public int id;
    public BigDecimal x_coordinance;
    public BigDecimal y_coordinance;
    public String nickname;
    public String file_location;
}

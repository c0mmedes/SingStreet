package com.ssafy.singstreet.studio.model;

import com.ssafy.singstreet.project.db.entity.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AudioBlockRequestDTO {
    public int block_id;
    public int test_id;
    public int project_id;
    public BigDecimal left;
    public BigDecimal top;
    public String nickname;
}

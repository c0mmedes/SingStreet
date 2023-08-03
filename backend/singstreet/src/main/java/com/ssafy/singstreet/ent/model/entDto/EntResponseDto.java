package com.ssafy.singstreet.ent.model.entDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntResponseDto {
    private int entId;
    private String entName;
    private String entImg;
    private String entInfo;
    private boolean isAutoAccepted;
    private List<String> tagNameList;

    public void update(List<String> tagNameList){
        this.tagNameList = tagNameList;
    }
}

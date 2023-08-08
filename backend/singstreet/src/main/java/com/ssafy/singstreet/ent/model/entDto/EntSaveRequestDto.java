package com.ssafy.singstreet.ent.model.entDto;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.entity.EntTag;
import com.ssafy.singstreet.user.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntSaveRequestDto {
    private String entName;
    private Boolean isAutoAccepted;
    private String entInfo;
    private String entImg;
    private String entTagList;

    @Builder
    public EntSaveRequestDto(String entName, Boolean isAutoAccepted,String entInfo,String entImg, String entTagList){
        this.entName = entName;
        this.isAutoAccepted = isAutoAccepted;
        this.entInfo = entInfo;
        this.entImg = entImg;
        this.entTagList = entTagList;
    }

}

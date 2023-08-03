package com.ssafy.singstreet.ent.model.entMemberDto;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.user.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntApplyRequestDto {
    private int userId;
    private int entId;
    private String hope;
    private String artist;
    private int age;
    private String content;
    private String audioName;

    public void updateUserId(int userId){
        this.userId = userId;
    }
}

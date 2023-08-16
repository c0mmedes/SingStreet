package com.ssafy.singstreet.ent.model.entFeedDto;

import com.ssafy.singstreet.user.db.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntFeedResponseDto {
    private int feedId;
    private int entId;
    private User userId;
    private String title;
    private String content;
    private boolean isNotice;
    private String filename;
    private LocalDateTime createdAt;

    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createdAt.format(formatter);
    }
}

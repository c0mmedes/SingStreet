package com.ssafy.singstreet.studio.db.entity;



import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.*;

@Builder
@Document(collection = "boxData")
public class BoxData {
    @Id
    private Integer id;

    private Integer projectId;
    private Integer x;
    private Integer y;
    private String file;
    private String uploader;
}

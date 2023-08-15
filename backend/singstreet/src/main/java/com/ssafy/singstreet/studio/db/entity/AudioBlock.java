package com.ssafy.singstreet.studio.db.entity;

import com.ssafy.singstreet.common.BaseTimeEntity;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.user.db.entity.User;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert // default
@Table(name = "audio_block")
public class AudioBlock extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id")
    private Integer blockId;

    @Column(name = "test_id")
    private Integer testId;

    @Column(name = "block_name", nullable = false)
    private String blockName;

    @Column(name = "audio_left", precision = 15, scale = 3)  // Adjust precision as needed.
    private BigDecimal left;

    @Column(name = "audio_top", precision = 15, scale = 3)  // Adjust precision as needed.
    private BigDecimal top;

    @Column(name = "file_location")
    private String fileLocation;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    ///////////////////////////////////////////
    @PrePersist
    private void prePersist(){
        if (isDeleted == null){
            isDeleted = false;
        }
    }

    @Builder
    public AudioBlock(Project project, User user, int testId, BigDecimal left, BigDecimal top, String fileLocation, String blockName){
        this.project = project;
        this.user = user;
        this.testId = testId;
        this.left =left;
        this.top = top;
        this.fileLocation = fileLocation;
        this.blockName = blockName;
    }

    public void delete(){
        this.isDeleted = true;
    }

    public void updateBlock(BigDecimal left, BigDecimal top) {
        this.left = left;
        this.top = top;
    }

    public void updateBlock(String blockName) {
        this.blockName = blockName;
    }
}
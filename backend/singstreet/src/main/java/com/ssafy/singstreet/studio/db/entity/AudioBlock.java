package com.ssafy.singstreet.studio.db.entity;

import com.ssafy.singstreet.project.db.entity.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "audioblock")
public class AudioBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id")
    private Integer blockId;

    @Column(name = "id")
    private Integer id;

    @Column(name = "left", precision = 15, scale = 3)  // Adjust precision as needed.
    private BigDecimal left;

    @Column(name = "top", precision = 15, scale = 3)  // Adjust precision as needed.
    private BigDecimal top;

    @Column(name = "file_location")
    private String fileLocation;

    @Column(name = "nickname")
    private String nickname;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "project_id")
    private Project project;  // Assuming there's a Project entity with a 'project_id' as primary key.

    // Constructors, getters, setters, and other methods

}
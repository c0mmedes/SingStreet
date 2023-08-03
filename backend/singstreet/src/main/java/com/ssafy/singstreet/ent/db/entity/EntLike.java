package com.ssafy.singstreet.ent.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "ent_like")
public class EntLike {
    @EmbeddedId
    private EntLikeId id;
}

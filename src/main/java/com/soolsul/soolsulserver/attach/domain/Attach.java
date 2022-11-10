package com.soolsul.soolsulserver.attach.domain;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attach extends BaseEntity {

    @Column(nullable = false)
    private String postId;

    @Column(nullable = false)
    private String tagId;

    public Attach(String postId, String tagId) {
        this.postId = postId;
        this.tagId = tagId;
    }
}

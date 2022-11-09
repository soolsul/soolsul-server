package com.soolsul.soolsulserver.post.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostScrap extends BaseTimeEntity {

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private String postId;

    public PostScrap(String ownerId, String postId) {
        this.ownerId = ownerId;
        this.postId = postId;
    }
}

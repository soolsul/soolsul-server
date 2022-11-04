package com.soolsul.soolsulserver.domain.post;

import com.soolsul.soolsulserver.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostView extends BaseEntity {

    @Column(nullable = false)
    private String viewerId;

    @Column(nullable = false)
    private String postId;

    public PostView(String viewerId, String postId) {
        this.viewerId = viewerId;
        this.postId = postId;
    }
}

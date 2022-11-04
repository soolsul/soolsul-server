package com.soolsul.soolsulserver.domain.post;

import com.soolsul.soolsulserver.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLike extends BaseEntity {

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private String postId;

    @Enumerated(value = EnumType.STRING)
    private PostLikeContent postLikeContent;
}

package com.soolsul.soolsulserver.post.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    private static final int LIMIT_LENGTH = 50;

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private String restaurantId;

    private Float score;

    @Column(length = LIMIT_LENGTH, nullable = false)
    private String title;

    @Lob
    @Column
    private String contents;

    public Post(String ownerId, String restaurantId, Float score, String contents) {
        this.ownerId = ownerId;
        this.restaurantId = restaurantId;
        this.score = score;
        this.contents = contents;
    }
}

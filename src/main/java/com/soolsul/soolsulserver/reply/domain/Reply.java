package com.soolsul.soolsulserver.reply.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseTimeEntity {

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private String postId;

    private String responseId;

    @Lob
    @Column
    private String contents;

    public Reply(String ownerId, String postId, String responseId, String contents) {
        this.ownerId = ownerId;
        this.postId = postId;
        this.responseId = responseId;
        this.contents = contents;
    }
}

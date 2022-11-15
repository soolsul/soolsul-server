package com.soolsul.soolsulserver.post.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostScrap extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private String postId;

    public PostScrap(String ownerId, String postId) {
        this.ownerId = ownerId;
        this.postId = postId;
    }
}

package com.soolsul.soolsulserver.post.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostScrap postScrap = (PostScrap) o;
        return Objects.equals(id, postScrap.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

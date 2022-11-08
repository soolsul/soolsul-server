package com.soolsul.soolsulserver.post.domain;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPhoto extends BaseEntity {

    @Column(nullable = false)
    private String restaurantId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;

    private String originalFileName;
    private String uuidFileUrl;
    private String extension;

    public PostPhoto(String restaurantId, String originalFileName, String uuidFileUrl, String extension) {
        this.restaurantId = restaurantId;
        this.originalFileName = originalFileName;
        this.uuidFileUrl = uuidFileUrl;
        this.extension = extension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostPhoto photo = (PostPhoto) o;
        return Objects.equals(uuidFileUrl, photo.uuidFileUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuidFileUrl);
    }
}

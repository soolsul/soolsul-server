package com.soolsul.soolsulserver.post.domain;

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
public class PostView {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String viewerId;

    @Column(nullable = false)
    private String postId;

    public PostView(String viewerId, String postId) {
        this.viewerId = viewerId;
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostView postView = (PostView) o;
        return Objects.equals(id, postView.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

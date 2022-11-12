package com.soolsul.soolsulserver.post.domain;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@SQLDelete(sql = "update post_photo set deleted = true where id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostPhoto extends BaseEntity {

    @Column(nullable = false)
    private String barId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;

    private String originalFileName;
    private String uuidFileUrl;
    private String extension;

    private Boolean deleted = Boolean.FALSE;

    public PostPhoto(String barId, String originalFileName, String uuidFileUrl, String extension) {
        this.barId = barId;
        this.originalFileName = originalFileName;
        this.uuidFileUrl = uuidFileUrl;
        this.extension = extension;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getUrl() {
        return uuidFileUrl;
    }
}

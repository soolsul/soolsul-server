package com.soolsul.soolsulserver.post.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.Positive;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    private static final int LIMIT_LENGTH = 50;

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private String barId;

    @Positive
    private Float score;

    @Lob
    @Column
    private String contents;

    @Embedded
    private PostPhotos photos = new PostPhotos();

    public Post(String ownerId, String barId, Float score, String contents) {
        this.ownerId = ownerId;
        this.barId = barId;
        this.score = score;
        this.contents = contents;
    }

    public void addPhoto(PostPhoto photo) {
        this.photos.addPhoto(photo);
        photo.setPost(this);
    }

    public List<PostPhoto> getPhotos() {
        return this.photos.getPhotos();
    }

    public void addPhotoList(List<PostPhoto> photos) {
        for (PostPhoto photo : photos) {
            this.photos.addPhoto(photo);
            photo.setPost(this);
        }
    }

    public void deletePhoto(PostPhoto photo) {
        this.photos.delete(photo);
        photo.setPost(null);
    }

    public void clearAllPhotos() {
        this.photos.clear();
    }
}

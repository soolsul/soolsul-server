package com.soolsul.soolsulserver.post.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import com.soolsul.soolsulserver.post.presentation.dto.PostCreateRequest;
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
    private String restaurantId;

    @Positive
    private Float score;

    @Lob
    @Column
    private String contents;

    @Embedded
    private PostPhotos photos = new PostPhotos();

    public Post(String ownerId, String restaurantId, Float score, String contents) {
        this.ownerId = ownerId;
        this.restaurantId = restaurantId;
        this.score = score;
        this.contents = contents;
    }

    //    public static Post of(String userId, String restaurantId, PostCreateRequest request) {
//        Post post = new Post(userId, restaurantId, request.getScore(), request.getPostContent());
//        return post;
//    }

    public void addPhoto(PostPhoto photo) {
        this.photos.addPhoto(photo);
    }

    public List<PostPhoto> getPhotos() {
        return this.photos.getPhotos();
    }

    public void addPhotoList(List<PostPhoto> photos) {
        for (PostPhoto photo : photos) {
            this.photos.addPhoto(photo);
        }
    }

    public void deletePhoto(PostPhoto photo) {
        this.photos.delete(photo);
    }

    public void clearAllPhotos() {
        this.photos.clear();
    }
}

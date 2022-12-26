package com.soolsul.soolsulserver.post.domain;

import com.soolsul.soolsulserver.common.domain.BaseTimeEntity;
import com.soolsul.soolsulserver.user.auth.domain.CustomUser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Objects;

@Entity
@SQLDelete(sql = "update post set deleted = true where id = ?")
@Where(clause = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    private static final int LIMIT_LENGTH = 50;

    @Id
    @Getter
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Getter
    @Column(nullable = false)
    private String ownerId;

    @Getter
    @Column(nullable = false)
    private String barId;

    @Getter
    @Positive
    private Float score;

    @Getter
    @Lob
    @Column
    private String contents;

    @Embedded
    private PostPhotos photos = new PostPhotos();

    @Embedded
    private Likes likes = new Likes();

    private Boolean deleted = Boolean.FALSE;

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

    public void like(CustomUser customUser) {
        this.likes.add(customUser.getId());
    }

    public void like(String userId) {
        this.likes.add(userId);
    }

    public void undoLike(CustomUser customUser) {
        this.likes.remove(customUser.getId());
    }

    public void undoLike(String userId) {
        this.likes.remove(userId);
    }

    public int likeCount() {
        return this.likes.size();
    }

    public boolean isLikeContain(String userId) {
        return likes.contains(userId);
    }

    public boolean isOwner(String userId) {
        return ownerId.equals(userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(getId(), post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

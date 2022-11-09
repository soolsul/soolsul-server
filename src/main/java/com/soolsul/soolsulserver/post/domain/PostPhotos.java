package com.soolsul.soolsulserver.post.domain;

import com.soolsul.soolsulserver.post.exception.MinimumPhotoCountException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PostPhotos {

    private static final int MINIMUM_PHOTO_COUNT = 1;

    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<PostPhoto> photos = new ArrayList<>();

    public void addPhoto(PostPhoto photo) {
        this.photos.add(photo);
    }

    public List<PostPhoto> getPhotos() {
        if (photos.isEmpty()) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(photos);
    }

    public void delete(PostPhoto photo) {
        if (this.photos.size() <= MINIMUM_PHOTO_COUNT) {
            throw new MinimumPhotoCountException();
        }
        this.photos.remove(photo);
    }

    public void clear() {
        this.photos.clear();
    }
}

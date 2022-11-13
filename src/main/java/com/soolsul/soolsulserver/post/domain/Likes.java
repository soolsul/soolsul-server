package com.soolsul.soolsulserver.post.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import java.util.HashSet;
import java.util.Set;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "post_id")
    )
    @Column(name = "like_user_id")
    private Set<String> likeUsers = new HashSet<>();

    public boolean add(String userId) {
        return likeUsers.add(userId);
    }

    public boolean remove(String userId) {
        return likeUsers.remove(userId);
    }

    public boolean contains(String userId) {
        return likeUsers.contains(userId);
    }

    public int size() {
        return likeUsers.size();
    }
}

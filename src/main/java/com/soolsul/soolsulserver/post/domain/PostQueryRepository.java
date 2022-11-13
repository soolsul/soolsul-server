package com.soolsul.soolsulserver.post.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostQueryRepository {
    Slice<Post> findPostListByLocation(List<String> barIds, Pageable pageable);
}

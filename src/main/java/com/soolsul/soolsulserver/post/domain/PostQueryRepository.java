package com.soolsul.soolsulserver.post.domain;

import com.soolsul.soolsulserver.post.domain.dto.FilteredPostLookupResponse;
import com.soolsul.soolsulserver.post.domain.dto.ScrapedPostLookUpResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostQueryRepository {
    Slice<FilteredPostLookupResponse> findPostListByLocation(List<String> barIds, Pageable pageable);

    List<ScrapedPostLookUpResponse> findAllScrapedPost(String userId);
}

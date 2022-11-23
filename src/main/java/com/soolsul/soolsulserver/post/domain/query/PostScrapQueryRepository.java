package com.soolsul.soolsulserver.post.domain.query;

import com.soolsul.soolsulserver.post.domain.dto.ScrapedPostLookUpResponse;

import java.util.List;

public interface PostScrapQueryRepository {
    List<ScrapedPostLookUpResponse> findAllScrapedPost(String userId);

}

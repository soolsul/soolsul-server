package com.soolsul.soolsulserver.post.domain.query;

import com.soolsul.soolsulserver.post.common.dto.response.ScrapedPostLookUpResponse;

import java.util.List;

public interface PostScrapQueryRepository {
    List<ScrapedPostLookUpResponse> findAllScrapedPost(String userId);

}

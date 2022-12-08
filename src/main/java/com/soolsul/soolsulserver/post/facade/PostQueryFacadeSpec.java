package com.soolsul.soolsulserver.post.facade;

import com.soolsul.soolsulserver.location.common.dto.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.post.common.dto.response.PostDetailResponse;
import com.soolsul.soolsulserver.post.common.dto.response.PostListResponse;
import org.springframework.data.domain.Pageable;

public interface PostQueryFacadeSpec {
    PostDetailResponse find(String userId, String id);

    PostListResponse findAll(String userId, LocationSquareRangeRequest locationSquareRangeRequest, Pageable pageable);
}

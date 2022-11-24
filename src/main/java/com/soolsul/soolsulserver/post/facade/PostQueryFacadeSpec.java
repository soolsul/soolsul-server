package com.soolsul.soolsulserver.post.facade;

import com.soolsul.soolsulserver.location.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.post.presentation.dto.PostDetailResponse;
import com.soolsul.soolsulserver.post.presentation.dto.PostListResponse;
import org.springframework.data.domain.Pageable;

public interface PostQueryFacadeSpec {
    PostDetailResponse find(String userId, String id);

    PostListResponse findAll(String userId, LocationSquareRangeRequest locationSquareRangeRequest, Pageable pageable);
}

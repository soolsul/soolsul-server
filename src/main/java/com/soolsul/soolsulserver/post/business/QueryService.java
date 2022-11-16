package com.soolsul.soolsulserver.post.business;

import com.soolsul.soolsulserver.common.userlocation.UserLocation;
import com.soolsul.soolsulserver.post.presentation.dto.PostDetailResponse;
import com.soolsul.soolsulserver.post.presentation.dto.PostListResponse;
import org.springframework.data.domain.Pageable;

public interface QueryService {
    PostDetailResponse find(String userId, String id);

    PostListResponse findAll(String userId, UserLocation userLocation, Pageable pageable);
}

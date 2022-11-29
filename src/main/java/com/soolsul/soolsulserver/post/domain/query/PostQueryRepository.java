package com.soolsul.soolsulserver.post.domain.query;

import com.soolsul.soolsulserver.curation.dto.CurationPostLookupResponse;
import com.soolsul.soolsulserver.post.domain.dto.FilteredPostLookupResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostQueryRepository {
    Slice<FilteredPostLookupResponse> findPostListByLocation(List<String> barIds, Pageable pageable);

    List<CurationPostLookupResponse> findAllPostByBarId(String barId);
}

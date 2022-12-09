package com.soolsul.soolsulserver.user.auth.persistence;

import com.soolsul.soolsulserver.user.auth.persistence.dto.response.UserLookUpResponse;

import java.util.Optional;

public interface UserQueryRepository {
    Optional<UserLookUpResponse> findUserDetailInfoById(String userId);
}

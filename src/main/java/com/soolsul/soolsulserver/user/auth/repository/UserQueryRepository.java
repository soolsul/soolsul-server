package com.soolsul.soolsulserver.user.auth.repository;

import com.soolsul.soolsulserver.user.auth.repository.dto.UserLookUpResponse;

import java.util.Optional;

public interface UserQueryRepository {
    Optional<UserLookUpResponse> findUserDetailInfoById(String userId);
}

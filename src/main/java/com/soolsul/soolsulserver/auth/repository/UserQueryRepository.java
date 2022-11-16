package com.soolsul.soolsulserver.auth.repository;

import com.soolsul.soolsulserver.auth.repository.dto.UserLookUpResponse;

import java.util.Optional;

public interface UserQueryRepository {
    Optional<UserLookUpResponse> findUserDetailInfoById(String userId);
}

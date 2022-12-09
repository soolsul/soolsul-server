package com.soolsul.soolsulserver.user.auth.persistence.dto.response;

public record UserLookUpResponse(
        String userId,
        String email,
        String password,
        String phone,
        String name,
        String nickName,
        String profileImage
) {
}

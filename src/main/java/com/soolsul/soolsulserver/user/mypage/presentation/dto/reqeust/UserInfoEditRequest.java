package com.soolsul.soolsulserver.user.mypage.presentation.dto.reqeust;

import javax.validation.constraints.NotBlank;

public record UserInfoEditRequest(
        @NotBlank String imageUrl,
        @NotBlank String nickName,
        @NotBlank String email
) {
}

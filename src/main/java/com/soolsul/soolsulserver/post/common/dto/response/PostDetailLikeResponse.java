package com.soolsul.soolsulserver.post.common.dto.response;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record PostDetailLikeResponse(

        @NotNull
        @Min(0)
        int count,

        @NotNull
        boolean userLikeStatus

) {
}

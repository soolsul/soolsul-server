package com.soolsul.soolsulserver.post.business.dto.response;

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

package com.soolsul.soolsulserver.post.business.dto.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record PostDetailStoreResponse(

        @NotEmpty
        String storeId,

        @NotBlank
        @Size(min = 1, max = 100)
        String storeName,

        String description
) {
}

package com.soolsul.soolsulserver.post.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    @NotBlank
    private String barId;

    @NotBlank
    @Size(min = 1, max = 500)
    private String postContent;

    @NotNull
    private Float score;

    @NotNull
    @PastOrPresent
    private LocalDate visitedDate;

    @Size(max = 8)
    private List<String> images;

    @Size(max = 10)
    private List<String> tags;
}

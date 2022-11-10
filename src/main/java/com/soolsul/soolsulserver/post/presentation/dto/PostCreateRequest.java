package com.soolsul.soolsulserver.post.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostCreateRequest {

    @NotBlank
    private String barId;

    @NotBlank
    @Size(min = 1, max = 500)
    private String postContent;

    @NotNull
    @Pattern(regexp = "1-5")
    private Float score;

    @NotNull
    @PastOrPresent
    @Pattern(regexp = "yyyy-MM-dd")
    private LocalDate visitedDate;

    @URL
    @Size(max = 8)
    private List<String> images;

    @Size(max = 10)
    private List<String> tags;
}

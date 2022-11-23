package com.soolsul.soolsulserver.images;

import com.soolsul.soolsulserver.images.exception.ImageCategoryNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum ImageCategory {

    RESTAURANT("restaurants"),
    USER("users");

    private final String categoryName;

    public static ImageCategory from(String categoryName) {
        return Arrays.stream(values())
                .filter(imageCategory -> Objects.equals(imageCategory.categoryName, categoryName))
                .findFirst()
                .orElseThrow(ImageCategoryNotFoundException::new);
    }

}

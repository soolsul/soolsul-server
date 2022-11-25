package com.soolsul.soolsulserver.images.vo;

import com.soolsul.soolsulserver.images.exception.InvalidImageExtensionException;

import java.util.Arrays;
import java.util.Objects;

public enum ImageExtension {

    PNG, JPG, GIF, SVG;

    private static final String EXTENSION_DELIMITER = ".";

    public static ImageExtension parseImageExtensionFromFilename(String originalFilename) {
        int extensionIndex = originalFilename.lastIndexOf(EXTENSION_DELIMITER);
        String imageExtensionName = originalFilename.substring(extensionIndex + 1);

        return Arrays.stream(values())
                .filter(imageExtension -> Objects.equals(imageExtension.getLowercaseName(), imageExtensionName))
                .findFirst()
                .orElseThrow(InvalidImageExtensionException::new);
    }

    public String getLowercaseName() {
        return this.name().toLowerCase();
    }

}

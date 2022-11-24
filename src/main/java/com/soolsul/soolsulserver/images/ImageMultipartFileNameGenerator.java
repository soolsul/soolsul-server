package com.soolsul.soolsulserver.images;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Component
public class ImageMultipartFileNameGenerator {

    private static final String FILE_NAME_FORMAT = "%s-%s.%s";
    private static final String EXTENSION_DELIMITER = ".";
    private static final String DASH = "-";

    private boolean isMultipartFileEmpty(MultipartFile multipartFile) {
        return (multipartFile == null || multipartFile.isEmpty())
                || (multipartFile.getOriginalFilename() == null || multipartFile.getOriginalFilename().isEmpty());
    }

    public String generateFileName(MultipartFile multipartFile) {
        if(isMultipartFileEmpty(multipartFile) || multipartFile.getOriginalFilename() == null) {
            throw new MultipartException("multipartFile is invalid");
        }

        String originalFilename = multipartFile.getOriginalFilename();
        int extensionIndex = originalFilename.lastIndexOf(EXTENSION_DELIMITER);

        String imageFileName = getFileName(originalFilename, extensionIndex);
        ImageExtension imageExtension = ImageExtension.parseImageExtensionFromFilename(originalFilename);
        String uuid = UUID.randomUUID().toString().replaceAll(DASH, "");

        return String.format(FILE_NAME_FORMAT, imageFileName, uuid, imageExtension.getLowercaseName());
    }

    private String getFileName(String originalFilename, int extensionIndex) {
        int fileStartIndex = 0;
        return originalFilename.substring(fileStartIndex, extensionIndex);
    }

}

package com.soolsul.soolsulserver.images;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.soolsul.soolsulserver.images.exception.ImageUploadFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsS3ImageHandler {

    private static final String FILE_URL_FORMAT = "%s/%s/%s";
    private static final String PREFIX_FORMAT = "%s/%s/";
    private static final String KEY_FORMAT = "%s/%s/%s";
    private static final String SLASH = "/";

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.images.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.images.endpoint.url}")
    private String endpointUrl;

    public String uploadImage(
            MultipartFile multipartFile,
            @Valid @NotNull ImageCategory category,
            @Valid @NotEmpty String fileName,
            @Valid @NotEmpty String id
    ) {
        String key = String.format(KEY_FORMAT, category, id, fileName);

        uploadFileTos3bucket(multipartFile, key);
        return generateFileUrl(fileName);
    }

    /*
     * @param category 최상위 디렉토리 (e.g. restaurant, user ...)
     * @param id 카테고리 하위 디렉토리 아이디
     */
    public List<String> findFileNames(String category, String id) {
        // 디렉토리 + 디렉토리 하위 파일 (e.g. restaurant/id/)
        String prefix = String.format(PREFIX_FORMAT, category, id);
        ObjectListing objectListing = getObjectListing(prefix);

        return objectListing.getObjectSummaries().stream()
                .map(S3ObjectSummary::getKey)
                .filter(key -> !key.equals(prefix))
                .collect(Collectors.toList());
    }

    private ObjectListing getObjectListing(String prefix) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(bucketName);
        listObjectsRequest.setPrefix(prefix);
        listObjectsRequest.setDelimiter(SLASH);
        return amazonS3.listObjects(listObjectsRequest);
    }

    private String generateFileUrl(String fileName) {
        return String.format(FILE_URL_FORMAT, endpointUrl, bucketName, fileName);
    }

    private void uploadFileTos3bucket(MultipartFile multipartFile, String uploadFilePath) {
        try {
            //uploadFilePath : 파일이 저장될 위치
            amazonS3.putObject(bucketName, uploadFilePath, multipartFile.getInputStream(), new ObjectMetadata());
        } catch (IOException ioException) {
            log.error("S3 bucket 에 이미지 파일을 업로드를 실패하였습니다.");
            throw new ImageUploadFailException();
        }

    }

}

package com.soolsul.soolsulserver.images;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsS3ImageHandler {

    private static final String FILE_URL_FORMAT = "%s/%s/%s";
    private static final String FILE_NAME_FORMAT = "%s-%s.%s";
    private static final String PREFIX_FORMAT = "%s/%s/";
    private static final String KEY_FORMAT = "%s/%s/%s";
    private static final String SLASH = "/";
    private static final String DASH = "-";

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.images.bucket.name}")
    private String bucketName;

    @Value("${aws.s3.images.endpoint.url}")
    private String endpointUrl;

    public String uploadImage(MultipartFile multipartFile, String category, String id) {
        String fileName = generateFileName(multipartFile);
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

    private boolean isMultipartFileEmpty(MultipartFile multipartFile) {
        return (multipartFile == null || multipartFile.isEmpty())
                || (multipartFile.getOriginalFilename() == null || multipartFile.getOriginalFilename().isEmpty());
    }

    private String generateFileName(MultipartFile multipartFile) {
        if(isMultipartFileEmpty(multipartFile) || multipartFile.getOriginalFilename() == null) {
            throw new MultipartException("multipartFile is invalid");
        }

        String originalFilename = multipartFile.getOriginalFilename();

        int fileStartIndex = 0;
        int extensionIndex = originalFilename.lastIndexOf(".");

        String fileName = originalFilename.substring(fileStartIndex, extensionIndex);
        String fileExtension = originalFilename.substring(extensionIndex + 1);
        String uuid = UUID.randomUUID().toString().replaceAll(DASH, "");

        return String.format(FILE_NAME_FORMAT, fileName, uuid, fileExtension);
    }

    private String generateFileUrl(String fileName) {
        return String.format(FILE_URL_FORMAT, endpointUrl, bucketName, fileName);
    }

    private void uploadFileTos3bucket(MultipartFile multipartFile, String uploadFilePath) {
        try {
            //uploadFilePath : 파일이 저장될 위치
            amazonS3.putObject(bucketName, uploadFilePath, multipartFile.getInputStream(), new ObjectMetadata());
        } catch (IOException e) {
            log.error("S3 bucket 에 이미지 파일을 업로드를 실패하였습니다.");
            throw new RuntimeException(e);
        }
    }
}

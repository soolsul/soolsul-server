package com.soolsul.soolsulserver.images;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = LocalStackS3Config.class)
class S3ImageUploaderTest {

    @Value("${aws.s3.images.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private AwsS3ImageUploader awsS3ImageUploader;

    @BeforeEach
    void init() {
        amazonS3.createBucket(bucketName);
        amazonS3.putObject(bucketName, "restaurant/user01", "");
    }

    @DisplayName("이미지를 s3에 업로드 한다.")
    @Test
    void uploadImageFile() {
        //given
        ImageCategory imageCategory = ImageCategory.from("restaurants");
        MockMultipartFile multipartFile = new MockMultipartFile(
                "multipartFile",
                "test1.PNG",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "test1".getBytes());

        //when
        String uploadImageUrl = awsS3ImageUploader.uploadImage(multipartFile, imageCategory, "test1", "user01");

        //then
        assertThat(uploadImageUrl).isNotEmpty();
    }

    @DisplayName("이미지 파일이 존재하지 않는다면 예외를 반환한다.")
    @Test
    void throwExceptionWhenImageNameIsEmpty() {
        //given, when, then
        assertThatExceptionOfType(MultipartException.class)
                .isThrownBy(() -> awsS3ImageUploader.uploadImage(null, ImageCategory.from("restaurants"), "imageName", "user01"));
    }

    public static Stream<String> originalEmptyNameProvider() {
        return Stream.of(null, "");
    }

    @DisplayName("이미지 파일 이름이 존재하지 않는다면 예외를 반환한다.")
    @MethodSource("originalEmptyNameProvider")
    @ParameterizedTest
    void throwExceptionWhenImageNameIsEmpty(String originalFileName) {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile(
                "multipartFile",
                originalFileName,
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "test1".getBytes());

        //when, then
        assertThatExceptionOfType(MultipartException.class)
                .isThrownBy(() -> awsS3ImageUploader.uploadImage(multipartFile, ImageCategory.from("restaurants"), "image1", "user01"));
    }

    @DisplayName("이미지를 검색한다")
    @Test
    void findFileNames() throws IOException {
        //given
        String imageName01 = "test1.PNG";
        String imageName02 = "test2.PNG";
        MockMultipartFile multipartFile01 = new MockMultipartFile("multipartFile", imageName01, MediaType.MULTIPART_FORM_DATA_VALUE, "test1".getBytes());
        MockMultipartFile multipartFile02 = new MockMultipartFile("multipartFile", imageName02, MediaType.MULTIPART_FORM_DATA_VALUE, "test1".getBytes());

        amazonS3.putObject(bucketName, "restaurant/user01/" + imageName01, multipartFile01.getInputStream(), new ObjectMetadata());
        amazonS3.putObject(bucketName, "restaurant/user01/" + imageName02, multipartFile02.getInputStream(), new ObjectMetadata());

        //when
        List<String> fileNames = awsS3ImageUploader.findFileNames("restaurant", "user01");

        //then
        assertThat(fileNames).hasSize(2);
    }
}

package com.soolsul.soolsulserver.images;

import com.soolsul.soolsulserver.images.component.ImageMultipartFileNameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ImageMultipartFileNameGeneratorTest {

    private ImageMultipartFileNameGenerator imageMultipartFileNameGenerator;

    @BeforeEach
    public void init() {
        this.imageMultipartFileNameGenerator = new ImageMultipartFileNameGenerator();
    }

    @DisplayName("multipartfile 에서 파일 이름을 추출한다")
    @Test
    void generateFileName() {
        //given
        String multipartName = "multipartFile";

        String imageName = "imageName";
        String imageFileExtension = ".png";
        String imageFileName = imageName + imageFileExtension;

        MockMultipartFile multipartFile = new MockMultipartFile(
                multipartName,
                imageFileName,
                MediaType.MULTIPART_FORM_DATA_VALUE,
                "image".getBytes());

        //when
        String generatedFileName = imageMultipartFileNameGenerator.generateFileName(multipartFile);

        //then
        assertThat(generatedFileName).startsWith(imageName);
    }

}

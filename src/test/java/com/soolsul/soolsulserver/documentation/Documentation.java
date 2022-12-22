package com.soolsul.soolsulserver.documentation;

import com.soolsul.soolsulserver.acceptance.AcceptanceTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;

import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;


@ExtendWith(RestDocumentationExtension.class)
public class Documentation extends AcceptanceTest {

    protected RequestSpecification spec;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        super.setUp();

        this.spec = new RequestSpecBuilder()
                .addFilter(documentationConfiguration(restDocumentation))
                .build();
    }
}

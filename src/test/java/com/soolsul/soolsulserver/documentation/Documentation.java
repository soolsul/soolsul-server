package com.soolsul.soolsulserver.documentation;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soolsul.soolsulserver.bar.presentation.BarQueryController;
import com.soolsul.soolsulserver.curation.presentation.CurationQueryController;
import com.soolsul.soolsulserver.documentation.base.DocumentTestConfig;
import com.soolsul.soolsulserver.post.presentation.PostController;
import com.soolsul.soolsulserver.reply.presentation.ReplyController;
import com.soolsul.soolsulserver.user.auth.presentation.AuthController;
import com.soolsul.soolsulserver.user.mypage.presentation.MyPageController;

@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@Import(DocumentTestConfig.class)
@WebMvcTest(controllers = {
    AuthController.class, BarQueryController.class, CurationQueryController.class, MyPageController.class,
    PostController.class, ReplyController.class},
    excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WebSecurityConfigurer.class)},
    excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class Documentation {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected WebApplicationContext context;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(this.context)
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .apply(documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withRequestDefaults(modifyUris().removePort(), prettyPrint())
                .withResponseDefaults(modifyUris().removePort(), prettyPrint()))
            .alwaysDo(print())
            .build();
    }
}

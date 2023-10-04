package com.soolsul.soolsulserver.acceptance;

import com.soolsul.soolsulserver.common.TestRedisContainer;
import com.soolsul.soolsulserver.data.DataLoader;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestExecutionListeners;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestExecutionListeners(value = {AcceptanceTestExecutionListener.class},
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
public class AcceptanceTest extends TestRedisContainer {

    protected static final String USER_EMAIL = "user@email.com";
    protected static final String USER_PASSWORD = "password";
    protected static final String NICK_NAME = "shine";

    protected static final String NAME = "user";

    @LocalServerPort
    int port;

    @Autowired
    private DataLoader dataLoader;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        dataLoader.loadData();
    }
}

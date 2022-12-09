package com.soolsul.soolsulserver.acceptance;

import com.soolsul.soolsulserver.data.DataLoader;
import com.soolsul.soolsulserver.data.DatabaseCleanup;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AcceptanceTest {

    protected static final String USER_EMAIL = "user@email.com";
    protected static final String USER_PASSWORD = "password";
    protected static final String NICK_NAME = "shine";

    protected static final String NAME = "user";
    protected static final String STORE_UUID = "store_uuid";

    @LocalServerPort
    int port;

    @Autowired
    private DatabaseCleanup databaseCleanup;

    @Autowired
    private DataLoader dataLoader;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();
        dataLoader.loadData();
    }
}

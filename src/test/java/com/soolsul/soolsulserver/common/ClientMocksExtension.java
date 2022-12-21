package com.soolsul.soolsulserver.common;

import com.soolsul.soolsulserver.common.mock.KakaoPlaceMocks;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ClientMocksExtension implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        KakaoPlaceMocks.startAllMocks();
    }

    @Override
    public void afterAll(ExtensionContext context) {
        KakaoPlaceMocks.removeAllMocks();
    }
}

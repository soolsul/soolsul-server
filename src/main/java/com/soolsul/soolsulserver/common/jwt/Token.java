package com.soolsul.soolsulserver.common.jwt;

public interface Token {
    String getAccessToken();
    String getRefreshToken();
}

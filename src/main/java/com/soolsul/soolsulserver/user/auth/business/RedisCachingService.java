package com.soolsul.soolsulserver.user.auth.business;

import java.time.Duration;

public interface RedisCachingService {

    void setValuesWithDuration(String key, String data, Duration duration);

    String getValues(String key);

    void deleteValues(String key);

    void addBlackList(String accessToken, Long milliSeconds);
}

package com.soolsul.soolsulserver.common.data;

import com.soolsul.soolsulserver.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.auth.presentation.dto.RegisterRequest;
import com.soolsul.soolsulserver.location.domain.LocationMagnificationLevel;
import com.soolsul.soolsulserver.location.persistence.LocationMagnificationLevelRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    private static final String STORE_UUID = "store_uuid";
    private static final String USER_EMAIL = "user@email.com";
    private static final String USER_PASSWORD = "password";
    private static final String NAME = "user";
    private static final String NICK_NAME = "shine";

    private final CustomUserDetailsService userDetailsService;
    private final LocationMagnificationLevelRepository locationMagnificationLevelRepositoryDsl;

    public void loadData() {
        log.info("[call DataLoader]");
        userDetailsService.register(new RegisterRequest(USER_EMAIL, USER_PASSWORD, "02-123-4567", NAME, NICK_NAME));

        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(1, 60));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(2, 90));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(3, 150));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(4, 300));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(5, 750));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(6, 1500));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(7, 3000));
        
        log.info("[init complete DataLoader]");
    }
}

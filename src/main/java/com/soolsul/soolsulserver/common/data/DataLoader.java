package com.soolsul.soolsulserver.common.data;

import com.soolsul.soolsulserver.location.domain.LocationMagnificationLevel;
import com.soolsul.soolsulserver.location.persistence.LocationMagnificationLevelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader {

    private final LocationMagnificationLevelRepository locationMagnificationLevelRepositoryDsl;

    @Transactional
    public void loadData() {
        log.debug("[call DataLoader]");

        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(1, 60));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(2, 90));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(3, 150));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(4, 300));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(5, 750));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(6, 1500));
        locationMagnificationLevelRepositoryDsl.save(new LocationMagnificationLevel(7, 3000));
    }

}

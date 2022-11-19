package com.soolsul.soolsulserver.location.persistence;

import com.soolsul.soolsulserver.location.domain.LocationMagnificationLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationMagnificationLevelRepository extends JpaRepository<LocationMagnificationLevel, Long> {
}

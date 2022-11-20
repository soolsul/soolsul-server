package com.soolsul.soolsulserver.location.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soolsul.soolsulserver.location.domain.LocationMagnificationLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.soolsul.soolsulserver.location.domain.QLocationMagnificationLevel.locationMagnificationLevel;

@Repository
@RequiredArgsConstructor
public class LocationMagnificationLevelRepositoryDsl {

    private final JPAQueryFactory jpaQueryFactory;

    public LocationMagnificationLevel findByMagnificationLevel(int level) {
        return jpaQueryFactory.selectFrom(locationMagnificationLevel)
                .where(locationMagnificationLevel.level.eq(level))
                .fetchOne();
    }

}

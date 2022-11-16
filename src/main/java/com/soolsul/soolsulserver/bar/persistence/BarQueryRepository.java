package com.soolsul.soolsulserver.bar.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soolsul.soolsulserver.bar.businees.dto.FilteredBarLookupResponse;
import com.soolsul.soolsulserver.bar.businees.dto.BarLookupServiceConditionRequest;
import com.soolsul.soolsulserver.bar.presentation.dto.BarLookupResponse;
import com.soolsul.soolsulserver.bar.presentation.dto.LocationLookupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.soolsul.soolsulserver.bar.domain.QBar.bar;
import static com.soolsul.soolsulserver.bar.domain.QBarAlcoholTag.barAlcoholTag;
import static com.soolsul.soolsulserver.bar.domain.QBarMoodTag.barMoodTag;

@Repository
@RequiredArgsConstructor
public class BarQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<BarLookupResponse> findById(String barId) {
        BarLookupResponse barLookupResponse = jpaQueryFactory.select(Projections.constructor(BarLookupResponse.class,
                        bar.id,
                        bar.regionId,
                        bar.barCategoryId,
                        bar.name,
                        bar.description,
                        Projections.constructor(LocationLookupResponse.class,
                                bar.location.latitude,
                                bar.location.longitude
                        )))
                .from(bar)
                .where(bar.id.eq(barId))
                .fetchOne();

        return Optional.ofNullable(barLookupResponse);
    }

    public List<FilteredBarLookupResponse> findBarFilteredByConditions(BarLookupServiceConditionRequest barLookupServiceConditionRequest) {
        double southWestLongitude = barLookupServiceConditionRequest.southWestLongitude();
        double southWestLatitude = barLookupServiceConditionRequest.southWestLatitude();
        double northEastLongitude = barLookupServiceConditionRequest.northEastLongitude();
        double northEastLatitude = barLookupServiceConditionRequest.northEastLatitude();
        List<String> barAlcoholTagIds = barLookupServiceConditionRequest.barAlcoholTagIds();
        List<String> barMoodTagIds = barLookupServiceConditionRequest.barMoodTagIds();

        return jpaQueryFactory.select(Projections.constructor(FilteredBarLookupResponse.class,
                        bar.id,
                        bar.name,
                        bar.description,
                        barAlcoholTag.id,
                        barAlcoholTag.alcoholCategoryName,
                        barMoodTag.id,
                        barMoodTag.name,
                        bar.createdAt
                        ))
                .from(bar)
                .leftJoin(barAlcoholTag).on(bar.id.eq(barAlcoholTag.barId))
                .leftJoin(barMoodTag).on(bar.id.eq(barMoodTag.barId))
                .where(
                        bar.location.longitude.between(southWestLongitude, northEastLongitude),
                        bar.location.latitude.between(southWestLatitude, northEastLatitude),
                        barAlcoholTag.id.in(barAlcoholTagIds),
                        barMoodTag.id.in(barMoodTagIds))
                .fetch();
    }
}

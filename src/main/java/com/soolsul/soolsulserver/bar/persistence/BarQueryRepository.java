package com.soolsul.soolsulserver.bar.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soolsul.soolsulserver.bar.businees.dto.FilteredBarLookupResponse;
import com.soolsul.soolsulserver.bar.businees.dto.BarLookupServiceConditionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.soolsul.soolsulserver.bar.domain.QBar.bar;
import static com.soolsul.soolsulserver.bar.domain.QBarAlcoholTag.barAlcoholTag;
import static com.soolsul.soolsulserver.bar.domain.QBarMoodTag.barMoodTag;

@Repository
@RequiredArgsConstructor
public class BarQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

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
                        barMoodTag.name))
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

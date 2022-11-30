package com.soolsul.soolsulserver.bar.persistence;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.soolsul.soolsulserver.bar.businees.dto.BarLookupServiceConditionRequest;
import com.soolsul.soolsulserver.bar.businees.dto.BarStreetNameAddressResponse;
import com.soolsul.soolsulserver.bar.businees.dto.FilteredBarLookupResponse;
import com.soolsul.soolsulserver.bar.presentation.dto.BarLookupResponse;
import com.soolsul.soolsulserver.bar.presentation.dto.LocationLookupResponse;
import com.soolsul.soolsulserver.curation.dto.BarOpeningHoursResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.soolsul.soolsulserver.bar.domain.QBar.bar;
import static com.soolsul.soolsulserver.bar.domain.QBarAlcoholTag.barAlcoholTag;
import static com.soolsul.soolsulserver.bar.domain.QBarMoodTag.barMoodTag;
import static com.soolsul.soolsulserver.bar.domain.QBarOpenTime.barOpenTime;

@Repository
@RequiredArgsConstructor
public class BarQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    //TODO service, controller 전용 dto 분리에 관한 부분 검토
    // - 이유 : web dto 에 관한 요구사항이 repository 까지 영향을 미친다 (비슷한 로직이고 필드만  다른 로직이 계속 생겨남)
    // - 객체 전체를 조회하는 DTO 를 통해 조회하고 service에서 web 전용으로 주입하는 방법에 관해 검토
    public Optional<BarLookupResponse> findById(String barId) {
        BarLookupResponse barLookupResponse = jpaQueryFactory.select(Projections.constructor(BarLookupResponse.class,
                                bar.id,
                                bar.regionId,
                                bar.barCategoryId,
                                bar.name,
                                bar.description,
                                bar.phoneNumber,
                                Projections.constructor(BarStreetNameAddressResponse.class,
                                        bar.streetNameAddress.regionName.as("regionName"),
                                        bar.streetNameAddress.city.as("city"),
                                        bar.streetNameAddress.district.as("district"),
                                        bar.streetNameAddress.roadName.as("roadName"),
                                        bar.streetNameAddress.roadNumber.as("roadNumber"),
                                        bar.streetNameAddress.roadNumberDetail.as("roadNumberDetail"),
                                        bar.streetNameAddress.locationDetail.as("locationDetail")),
                                Projections.constructor(BarOpeningHoursResponse.class,
                                        barOpenTime.openingHours.openTime,
                                        barOpenTime.openingHours.closeTime),
                                Projections.constructor(LocationLookupResponse.class,
                                        bar.location.latitude,
                                        bar.location.longitude)
                        ))
                .from(bar)
                .leftJoin(barOpenTime).on(bar.id.eq(barOpenTime.barId))// TODO 조인을 제거할 필요 고민.
                .where(bar.id.eq(barId))
                .fetchOne();

        return Optional.ofNullable(barLookupResponse);
    }

    public List<FilteredBarLookupResponse> findBarFilteredByConditions(BarLookupServiceConditionRequest barLookupServiceConditionRequest) {
        double southWestLatitude = barLookupServiceConditionRequest.southWestLatitude(); // minY
        double southWestLongitude = barLookupServiceConditionRequest.southWestLongitude(); // minX
        double northEastLatitude = barLookupServiceConditionRequest.northEastLatitude(); // maxY
        double northEastLongitude = barLookupServiceConditionRequest.northEastLongitude(); // maxX

        List<String> barAlcoholTagIds = barLookupServiceConditionRequest.barAlcoholTagIds();
        List<String> barMoodTagIds = barLookupServiceConditionRequest.barMoodTagIds();

        return jpaQueryFactory.select(Projections.constructor(FilteredBarLookupResponse.class,
                        bar.id,
                        bar.name,
                        bar.description,
                        bar.location.latitude,
                        bar.location.longitude,
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
                        inBarAlcoholTagIds(barAlcoholTagIds),
                        inBarMoodTagIds(barMoodTagIds)
                ).fetch();
    }

    private BooleanExpression inBarAlcoholTagIds(List<String> barAlcoholTagIds) {
        if (barAlcoholTagIds == null || barAlcoholTagIds.isEmpty()) {
            return null;
        }
        return barAlcoholTag.id.in(barAlcoholTagIds);
    }

    private BooleanExpression inBarMoodTagIds(List<String> barMoodTagIds) {
        if (barMoodTagIds == null || barMoodTagIds.isEmpty()) {
            return null;
        }
        return barMoodTag.id.in(barMoodTagIds);
    }

}

package com.soolsul.soolsulserver.business;

import com.soolsul.soolsulserver.location.business.LocationRangeService;
import com.soolsul.soolsulserver.location.domain.LocationMagnificationLevel;
import com.soolsul.soolsulserver.location.persistence.LocationMagnificationLevelRepositoryDsl;
import com.soolsul.soolsulserver.location.common.dto.request.LocationSquareRangeRequest;
import com.soolsul.soolsulserver.location.common.dto.response.LocationSquareRangeCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LocationRangeServiceTest {

    private LocationRangeService locationRangeService;

    @Mock
    private LocationMagnificationLevelRepositoryDsl locationMagnificationLevelRepositoryDsl;

    @BeforeEach
    void init() {
        this.locationRangeService = new LocationRangeService(locationMagnificationLevelRepositoryDsl);
    }

    @DisplayName("확대 레벨을 기반으로 경위도 범위를 제공한다")
    @Test
    void calculate_location_square_range () {
        //given
        LocationMagnificationLevel magnificationLevel3 = new LocationMagnificationLevel(3, 250);
        LocationSquareRangeRequest locationRangeRequest = new LocationSquareRangeRequest(37.565494, 126.992493, magnificationLevel3.getLevel());

        given(locationMagnificationLevelRepositoryDsl.findByMagnificationLevel(anyInt())).willReturn(magnificationLevel3);

        //when
        LocationSquareRangeCondition locationSquareRangeCondition
                = locationRangeService.calculateLocationSquareRange(locationRangeRequest);

        //then
        assertAll(
                () -> assertThat(locationSquareRangeCondition.southWestLatitude()).isEqualTo(37.5632456959852),
                () -> assertThat(locationSquareRangeCondition.southWestLongitude()).isEqualTo(37.5677423040148),
                () -> assertThat(locationSquareRangeCondition.northEastLatitude()).isEqualTo(126.98875647809136),
                () -> assertThat(locationSquareRangeCondition.northEastLongitude()).isEqualTo(126.99622952190863)
        );

    }

}

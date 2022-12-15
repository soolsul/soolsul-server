package com.soolsul.soolsulserver.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.soolsul.soolsulserver.acceptance.BarStep.술집_목록_요청;
import static com.soolsul.soolsulserver.acceptance.BarStep.바_목록_조회_응답;

public class BarAcceptanceTest extends AcceptanceTest {

    @DisplayName("기본으로 바 메뉴를 접속하면 사용자 위치를 기반으로 바 목록을 조회한다.")
    @Test
    void find_bar_filtered_by_conditions() {
        double latitude = 37.5;
        double longitude = 120;
        String batMoodTagNames = "";
        String barAlcoholTagNames = "";

        var 술집_목록_응답 = 술집_목록_요청(latitude, longitude, batMoodTagNames, barAlcoholTagNames);

        바_목록_조회_응답(술집_목록_응답);
    }

}

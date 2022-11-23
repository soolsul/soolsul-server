package com.soolsul.soolsulserver.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.soolsul.soolsulserver.acceptance.AuthStep.로그인_되어_있음;
import static com.soolsul.soolsulserver.acceptance.MyPageStep.스크랩_피드_응답_확인;
import static com.soolsul.soolsulserver.acceptance.MyPageStep.스크랩_피드_조회_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_스크랩_요청;
import static com.soolsul.soolsulserver.common.data.DataLoader.postIdOne;
import static com.soolsul.soolsulserver.common.data.DataLoader.postIdTwo;

public class MyPageAcceptanceTest extends AcceptanceTest {

    /**
     * given: 등록된 피드가 있고
     * and: 사전에 스크랩된 피드 목록이 있다.
     * and: 마이페이지를 보고있는 User가 있다.
     * when: “저장함” 버튼을 누르면
     * then: 저장함에 저장된 피드 리스트가 뜬다.
     */
    @DisplayName("스크랩된 피드를 전부 가져온다.")
    @Test
    public void scraped_post_find_all_test() {
        // given
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);
        피드_스크랩_요청(accessToken, postIdOne);
        피드_스크랩_요청(accessToken, postIdTwo);

        // when
        var 스크랩_피드_응답 = 스크랩_피드_조회_요청(accessToken);

        // then
        스크랩_피드_응답_확인(스크랩_피드_응답);
    }
}

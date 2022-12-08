package com.soolsul.soolsulserver.acceptance;

import com.soolsul.soolsulserver.post.common.dto.request.PostCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.soolsul.soolsulserver.acceptance.AuthStep.로그인_되어_있음;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_단건_조회_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_단건_조회_응답_확인;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_목록_조회_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_생성_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_생성_응답_확인;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_생성_정보_생성;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_스크랩_요청;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_스크랩_응답_확인;
import static com.soolsul.soolsulserver.acceptance.PostStep.피드_조회_응답_확인;


public class PostAcceptanceTest extends AcceptanceTest {

    /**
     * given: 사용자가 로그인 한 상태이다.
     * and: 해당 가게가 이미 등록되어 있다.
     * and: 사용자가 본문을 작성하였다.
     * when: 작성 완료를 누르면
     * then: 성공적으로 저장되고, 피드 리스트 페이지로 이동한다.
     */
    @DisplayName("사용자가 피드를 작성후 작성 완료를 누르는 경우 서버에 정상저장 된다.")
    @Test
    public void create_post_test() {
        // given
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);
        PostCreateRequest postCreateRequest = 피드_생성_정보_생성();

        // when
        var response = 피드_생성_요청(accessToken, postCreateRequest);

        // then
        피드_생성_응답_확인(response);
    }

    /**
     * given: 사용자가 로그인 한 상태이다.
     * and: 사용자가 첫 지도화면에 있는 상태이다.
     * when: 네비게이션의 피드를 누른다.
     * then: 피드 리스트 페이지로 이동한다.
     * when: User가 리스트에서 특정 가게의 단건 피드을 누른다.
     * then: 해당 단건 피드로 이동한다.
     * when: 해당 단건 피드를 관심목록에 스크랩 한다.
     * then: 성공적으로 스크랩 된다.
     */
    @DisplayName("사용자가 피드 스토리 테스트")
    @Test
    public void find_post_list_test() {
        // given
        String accessToken = 로그인_되어_있음(USER_EMAIL, USER_PASSWORD);
        피드_생성_요청(accessToken, 피드_생성_정보_생성());

        // when
        var 피드_목록_조회_응답 = 피드_목록_조회_요청(accessToken);

        // then
        String 첫_피드_아이디 = 피드_조회_응답_확인(피드_목록_조회_응답);

        // when
        var 피드_단건_조회_응답 = 피드_단건_조회_요청(accessToken, 첫_피드_아이디);

        // then
        피드_단건_조회_응답_확인(피드_단건_조회_응답);

        // when
        var 피드_스크랩_응답 = 피드_스크랩_요청(accessToken, 첫_피드_아이디);

        // then
        피드_스크랩_응답_확인(피드_스크랩_응답);
    }
}

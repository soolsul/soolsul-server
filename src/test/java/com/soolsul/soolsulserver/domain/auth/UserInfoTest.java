package com.soolsul.soolsulserver.domain.auth;

import com.soolsul.soolsulserver.user.auth.domain.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class UserInfoTest {

    @DisplayName("생성 테스트")
    @Test
    public void user_info_create_test() {
        assertThatCode(() -> new UserInfo("user_id", "010-1234-5678", "shine", "tester"))
                .doesNotThrowAnyException();
    }

    @DisplayName("ID가 같은 경우 equals를 통해 동일한 객체로 판단하는지 확인한다.")
    @Test
    public void compare_equal_test() {
        UserInfo userInfoOne = createUserInfo("uuid", "user_1");
        UserInfo userInfoTwo = createUserInfo("uuid", "user_2");
        assertThat(userInfoOne).isEqualTo(userInfoTwo);
    }

    @DisplayName("ID가 다른 경우 equals를 통해 다른 객체로 판단한다.")
    @Test
    public void compare_not_equal_test() {
        UserInfo userInfoOne = createUserInfo("uuid1", "user_1");
        UserInfo userInfoTwo = createUserInfo("uuid2", "user_1");
        assertThat(userInfoOne).isNotEqualTo(userInfoTwo);
    }

    private UserInfo createUserInfo(String id, String userId) {
        UserInfo userInfo = new UserInfo(userId, "010-1234-5678", "shine", "tester");
        ReflectionTestUtils.setField(userInfo, "id", id);
        return userInfo;
    }
}

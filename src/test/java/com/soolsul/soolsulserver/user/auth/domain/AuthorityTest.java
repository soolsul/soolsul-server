package com.soolsul.soolsulserver.user.auth.domain;


import com.soolsul.soolsulserver.user.auth.vo.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatCode;

class AuthorityTest {

    @DisplayName("Authority 생성 테스트")
    @Test
    public void authority_create_test() {
        assertThatCode(() -> new Authority(Role.USER))
                .doesNotThrowAnyException();
    }

    @DisplayName("Role이 같은 경우 equals를 통해 동일한 객체로 판단하는지 확인한다.")
    @Test
    public void compare_equal_test() {
        Authority userAuthorityOne = createAuthority("1", Role.USER);
        Authority userAuthorityTwo = createAuthority("2", Role.USER);
        Assertions.assertThat(userAuthorityOne).isEqualTo(userAuthorityTwo);
    }

    @DisplayName("Role 다른 경우 equals를 통해 다른 객체로 판단한다.")
    @Test
    public void compare_not_equal_test() {
        Authority userAuthority = createAuthority("1", Role.USER);
        Authority adminAuthority = createAuthority("1", Role.ADMIN);
        Assertions.assertThat(userAuthority).isNotEqualTo(adminAuthority);
    }

    private Authority createAuthority(String id, Role role) {
        Authority authority = new Authority(role);
        ReflectionTestUtils.setField(authority, "id", id);
        return authority;
    }
}

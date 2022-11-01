package com.soolsul.soolsulserver.domain.auth;

import com.soolsul.soolsulserver.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAuthority extends BaseEntity {
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    private User user;

    @Enumerated(value = EnumType.STRING)
    private Authority authority;
    public Role getAuthority() {
        return authority.getRole();
    }


}

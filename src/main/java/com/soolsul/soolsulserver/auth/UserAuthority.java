package com.soolsul.soolsulserver.auth;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
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

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Authority authority;
    public Role getAuthority() {
        return authority.getRole();
    }

    public Authority getGrantedAuthority() {
        return authority;
    }

}

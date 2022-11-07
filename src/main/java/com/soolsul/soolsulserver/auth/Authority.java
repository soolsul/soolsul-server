package com.soolsul.soolsulserver.auth;

import com.soolsul.soolsulserver.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority extends BaseEntity implements GrantedAuthority {
    @Enumerated(value = EnumType.STRING)
    private Role authority;

    @Override
    public String getAuthority() {
        return authority.name();
    }

    public Role getRole(){return authority;}
}

package com.soolsul.soolsulserver.domain.auth;

import com.soolsul.soolsulserver.domain.common.BaseEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;


@Entity
public class Authority extends BaseEntity implements GrantedAuthority {
    private Role authority;

    @Override
    public String getAuthority() {
        return authority.name();
    }

    public Role getRole(){return authority;}
}

package com.soolsul.soolsulserver.domain.auth;

import org.springframework.security.core.GrantedAuthority;


public class Authority implements GrantedAuthority {
    private Role authority;

    @Override
    public String getAuthority() {
        return authority.name();
    }

    public Role getRole(){return authority;}
}

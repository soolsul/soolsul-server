package com.soolsul.soolsulserver.user.auth.vo;

import com.soolsul.soolsulserver.user.auth.domain.CustomUser;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UserContext extends User {

    private CustomUser user;

    public UserContext(CustomUser user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }
}

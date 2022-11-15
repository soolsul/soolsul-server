package com.soolsul.soolsulserver.auth;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String id;

    @Enumerated(value = EnumType.STRING)
    private Role authority;

    public Authority(Role authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority.getRole();
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userRoles")
    private Set<CustomUser> customUserDetails = new HashSet<>();
}

package com.soolsul.soolsulserver.auth.provider;

import com.soolsul.soolsulserver.auth.CustomUser;
import com.soolsul.soolsulserver.auth.UserContext;
import com.soolsul.soolsulserver.auth.business.CustomUserDetailsService;
import com.soolsul.soolsulserver.auth.token.FirstLoginAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

public class FirstLoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        FirstLoginAuthenticationToken firstLoginAuthenticationToken = (FirstLoginAuthenticationToken) authentication;
        String email = firstLoginAuthenticationToken.getName();
        String password = (String) firstLoginAuthenticationToken.getCredentials();

        UserContext accountContext = (UserContext) userDetailsService.loadUserByUsername(email);
        CustomUser user = accountContext.getUser();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new FirstLoginAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(FirstLoginAuthenticationToken.class);
    }
}

package com.soolsul.soolsulserver.auth.provider;

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

        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserContext accountContext = (UserContext) userDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(password, accountContext.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new FirstLoginAuthenticationToken(accountContext.getUser(), null, accountContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(FirstLoginAuthenticationToken.class);
    }
}

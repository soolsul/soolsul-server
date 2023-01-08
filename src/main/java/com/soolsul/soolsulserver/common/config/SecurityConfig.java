package com.soolsul.soolsulserver.common.config;


import com.soolsul.soolsulserver.user.auth.vo.Role;
import com.soolsul.soolsulserver.user.auth.common.JwtAuthenticationEntryPoint;
import com.soolsul.soolsulserver.user.auth.filter.LoginAuthenticationFilter;
import com.soolsul.soolsulserver.user.auth.filter.JwtAuthenticationFilter;
import com.soolsul.soolsulserver.user.auth.handler.LoginAuthenticationFailureHandler;
import com.soolsul.soolsulserver.user.auth.handler.LoginAuthenticationSuccessHandler;
import com.soolsul.soolsulserver.user.auth.handler.JwtDeniedHandler;
import com.soolsul.soolsulserver.user.auth.handler.JwtLogoutHandler;
import com.soolsul.soolsulserver.user.auth.handler.JwtLogoutSuccessHandler;
import com.soolsul.soolsulserver.user.auth.provider.LoginAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_GET_URI = {
            "/api/bars", "/api/curations", "/api/posts/**", "/actuator/**"
    };

    private static final String[] PUBLIC_POST_URI = {
            "/api/auth/register"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, PUBLIC_GET_URI).permitAll()
                .antMatchers(HttpMethod.POST, PUBLIC_POST_URI).permitAll()
                .antMatchers("/api/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .accessDecisionManager(affirmativeBased())
                .and()
                .addFilterAt(firstLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), LoginAuthenticationFilter.class);

        http
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint())
                .accessDeniedHandler(jwtDeniedHandler());

        http
                .logout()
                .logoutUrl("/api/auth/logout")
                .addLogoutHandler(jwtLogoutHandler())
                .logoutSuccessHandler(jwtLogoutSuccessHandler());
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui/**", "/v1/api-docs/**");
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public LoginAuthenticationFilter firstLoginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter();
        loginAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        loginAuthenticationFilter.setAuthenticationSuccessHandler(firstLoginAuthenticationSuccessHandler());
        loginAuthenticationFilter.setAuthenticationFailureHandler(firstLoginAuthenticationFailureHandler());
        return loginAuthenticationFilter;
    }

    @Bean
    public LoginAuthenticationSuccessHandler firstLoginAuthenticationSuccessHandler() {
        return new LoginAuthenticationSuccessHandler();
    }

    @Bean
    public LoginAuthenticationFailureHandler firstLoginAuthenticationFailureHandler() {
        return new LoginAuthenticationFailureHandler();
    }

    @Bean
    public JwtDeniedHandler jwtDeniedHandler() {
        return new JwtDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider firstLoginAuthenticationProvider() {
        return new LoginAuthenticationProvider();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public JwtLogoutHandler jwtLogoutHandler() {
        return new JwtLogoutHandler();
    }

    @Bean
    public JwtLogoutSuccessHandler jwtLogoutSuccessHandler() {
        return new JwtLogoutSuccessHandler();
    }

    // Role 계층 권한 설정 시작
    private AccessDecisionManager affirmativeBased() {
        return new AffirmativeBased(getAccessDecisionVoters());
    }

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
        accessDecisionVoters.add(roleVoter());
        return accessDecisionVoters;
    }

    @Bean
    public AccessDecisionVoter<? extends Object> roleVoter() {
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy());

        WebExpressionVoter webExpressVoter = new WebExpressionVoter();
        webExpressVoter.setExpressionHandler(handler);

        return webExpressVoter;
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy() {
        return new RoleHierarchyImpl();
    }
    // Role 계층 권한 설정 끝
}

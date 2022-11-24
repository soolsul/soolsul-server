package com.soolsul.soolsulserver.config;


import com.soolsul.soolsulserver.user.auth.common.JwtAuthenticationEntryPoint;
import com.soolsul.soolsulserver.user.auth.filter.FirstLoginAuthenticationFilter;
import com.soolsul.soolsulserver.user.auth.filter.JwtAuthenticationFilter;
import com.soolsul.soolsulserver.user.auth.handler.FirstLoginAuthenticationFailureHandler;
import com.soolsul.soolsulserver.user.auth.handler.FirstLoginAuthenticationSuccessHandler;
import com.soolsul.soolsulserver.user.auth.handler.JwtDeniedHandler;
import com.soolsul.soolsulserver.user.auth.handler.JwtLogoutHandler;
import com.soolsul.soolsulserver.user.auth.handler.JwtLogoutSuccessHandler;
import com.soolsul.soolsulserver.user.auth.provider.FirstLoginAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_URI = {
            "/api/auth/register", "/actuator/**"
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
                .antMatchers(PUBLIC_URI).permitAll()
//                .antMatchers("/api/**").hasRole(Role.USER.name())
                .antMatchers("/api/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterAt(firstLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), FirstLoginAuthenticationFilter.class);

        http
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .accessDeniedHandler(jwtDeniedHandler());

        http
                .logout()
                .logoutUrl("/api/auth/logout")
                .addLogoutHandler(jwtLogoutHandler())
                .logoutSuccessHandler(jwtLogoutSuccessHandler());
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public FirstLoginAuthenticationFilter firstLoginAuthenticationFilter() throws Exception {
        FirstLoginAuthenticationFilter firstLoginAuthenticationFilter = new FirstLoginAuthenticationFilter();
        firstLoginAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        firstLoginAuthenticationFilter.setAuthenticationSuccessHandler(firstLoginAuthenticationSuccessHandler());
        firstLoginAuthenticationFilter.setAuthenticationFailureHandler(firstLoginAuthenticationFailureHandler());
        return firstLoginAuthenticationFilter;
    }

    @Bean
    public FirstLoginAuthenticationSuccessHandler firstLoginAuthenticationSuccessHandler() {
        return new FirstLoginAuthenticationSuccessHandler();
    }

    @Bean
    public FirstLoginAuthenticationFailureHandler firstLoginAuthenticationFailureHandler() {
        return new FirstLoginAuthenticationFailureHandler();
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
        return new FirstLoginAuthenticationProvider();
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
}

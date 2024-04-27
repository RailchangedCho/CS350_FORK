package com.example.fork.config;

import com.softdev.folletto.endUserappapiserver.global.auth.AuthProvider;
import com.softdev.folletto.endUserappapiserver.global.auth.JwtRequestFilter;
import com.softdev.folletto.endUserappapiserver.global.auth.Role;
import com.softdev.folletto.endUserappapiserver.global.data.repository.EndUserRepository;
import com.softdev.folletto.endUserappapiserver.global.data.repository.TokenRepository;
import com.softdev.folletto.endUserappapiserver.global.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final EndUserRepository endUserRepository;
    private final TokenRepository tokenRepository;

    public static final String FRONT_URL = "http://localhost:3000";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .cors().configurationSource(new CorsConfig());

        http.authorizeRequests()
                .antMatchers(FRONT_URL+"/main/**")
                .authenticated()
                .anyRequest().permitAll()
                .and()
                //(1)
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        //(2)
        http.addFilterBefore(new JwtRequestFilter(endUserRepository, tokenRepository), UsernamePasswordAuthenticationFilter.class);
    }
}

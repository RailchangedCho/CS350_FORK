package com.example.fork.global.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.fork.global.data.repository.TokenRepository;
import com.example.fork.global.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.auth.AUTH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collections;

@RequiredArgsConstructor
@Component
@Log4j2
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwtHeader = ((HttpServletRequest)request).getHeader(JwtProperties.HEADER_STRING);
        if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtHeader.replace(JwtProperties.TOKEN_PREFIX, "");
        Long id = null;
        //(7)
        try {
            id = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(token)
                    .getClaim("id").asLong();
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            request.setAttribute(JwtProperties.HEADER_STRING, "토큰이 만료되었습니다.");
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            request.setAttribute(JwtProperties.HEADER_STRING, "유효하지 않은 토큰입니다.");
        }
        request.setAttribute("id", id);

        AuthProvider authProvider = new AuthProvider(userRepository, tokenRepository);
        if (!authProvider.isTokenValid(token)) {
            throw new TokenExpiredException("비정상적인 접근입니다.", Instant.now());
        }

//        try {
//            filterChain.doFilter(request, response);
//        } catch (JWTDecodeException e) {
//            e.printStackTrace();
//            request.setAttribute(JwtProperties.HEADER_STRING, "유효하지 않은 토큰입니다.");
//        }

        /* extract role */
        String role = extractRoleFromToken(token);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(extractIdFromToken(token), null, Collections.singletonList(authority));
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private String extractRoleFromToken(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                .build()
                .verify(token);
       return jwt.getClaim("role").asString();
    }

    private String extractIdFromToken(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
                .build()
                .verify(token);
        return jwt.getClaim("id").asString();
    }


}
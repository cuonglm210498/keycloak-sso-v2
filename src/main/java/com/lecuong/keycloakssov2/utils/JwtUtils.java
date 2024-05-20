package com.lecuong.keycloakssov2.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    HttpServletRequest request;

    public String getSub() {
        return jwtTokenProvider.getSub(this.getToken());
    }

    private String getToken() {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        return token.replace("Bearer ", "");
    }
}

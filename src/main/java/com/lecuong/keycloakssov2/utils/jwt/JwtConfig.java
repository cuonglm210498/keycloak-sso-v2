package com.lecuong.keycloakssov2.utils.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class JwtConfig {

    @Value("${jwt.keycloak.publicKey}")
    private String publicKey;
}

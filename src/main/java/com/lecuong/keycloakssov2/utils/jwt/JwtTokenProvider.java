package com.lecuong.keycloakssov2.utils.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    JwtConfig jwtConfig;

    public String getSub(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.readKeycloakPublicKey(jwtConfig.getPublicKey()))
                .parseClaimsJws(token)
                .getBody();
        return claims.get("sub").toString();
    }

    public String getEmail(String token) {
        Claims claims = null;
        try {
            claims = this.verifyKeycloakToken(token, jwtConfig.getPublicKey());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return claims != null ? claims.get("email").toString() : null;
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(this.readKeycloakPublicKey(jwtConfig.getPublicKey())).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }

    public static Claims verifyKeycloakToken(String token, String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        byte[] publicKey = Base64.decodeBase64(publicKeyStr);
        PublicKey key = kf.generatePublic(new X509EncodedKeySpec(publicKey));
        Claims claim = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claim;
    }

    public static Claims verifyKeycloakToken(String token, byte[] publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey key = kf.generatePublic(new X509EncodedKeySpec(publicKey));
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    public PublicKey readKeycloakPublicKey(String publicKeyStr) {
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            byte[] publicKeyBytes = Base64.decodeBase64(publicKeyStr);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            return kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

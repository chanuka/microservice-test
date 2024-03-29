package com.cba.core.auth.util;

import com.cba.core.auth.config.JwtConfig;
import com.cba.core.auth.exception.JwtTokenException;
import com.cba.core.auth.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final JwtConfig jwtConfig;
    private final CustomUserDetailsService customUserDetailsService;

    public String generateTokenFromUsername(String username, JwtEncoder encoder) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(jwtConfig.getTokenExpirationAfterMillis(), ChronoUnit.MILLIS))
                .subject(username)
                .claim("authorities", customUserDetailsService.loadUserByUsername(username).getAuthorities())
                .claim("Validity", DeviceTypeEnum.WEB.getValue())
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateTokenFromAuthResult(Authentication authResult, JwtEncoder encoder) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(jwtConfig.getTokenExpirationAfterMillis(), ChronoUnit.MILLIS))
                .subject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .claim("Validity", DeviceTypeEnum.WEB.getValue())
                .build();
        return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Jwt validateJwtToken(String authToken, JwtDecoder decoder) throws JwtTokenException {
        try {
            return decoder.decode(authToken);
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw new JwtTokenException(authToken, e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new JwtTokenException(authToken, e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new JwtTokenException(authToken, e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
            throw new JwtTokenException(authToken, e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            throw new JwtTokenException(authToken, e.getMessage());
        } catch (JwtValidationException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new JwtTokenException(authToken, e.getMessage());
        }
    }
}

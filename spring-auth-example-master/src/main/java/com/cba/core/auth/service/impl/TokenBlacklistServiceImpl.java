package com.cba.core.auth.service.impl;

import com.cba.core.auth.dao.TokenBlacklistDao;
import com.cba.core.auth.model.TokenBlacklist;
import com.cba.core.auth.service.TokenBlacklistService;
import com.cba.core.auth.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private final TokenBlacklistDao dao;
    private final JwtUtil jwtUtil;
    private final JwtDecoder decoder;

    @Override
    public TokenBlacklist createBlacklistToken(String token) throws Exception {
        TokenBlacklist tokenBlacklist = null;
        if (isTokenBlacklisted(token)) {
            return tokenBlacklist;
        }
        tokenBlacklist = new TokenBlacklist();
        tokenBlacklist.setToken(token);
        tokenBlacklist.setExpiration(extractTokenExpiration(token));

        tokenBlacklist = dao.create(tokenBlacklist);
        return tokenBlacklist;

    }

    @Override
    public boolean isTokenBlacklisted(String token) throws Exception {
        try {
            TokenBlacklist tokenBlacklist = dao.findByToken(token);
            return tokenBlacklist != null
                    && tokenBlacklist.getExpiration().isAfter(Instant.now());
        } catch (Exception ee) {
            ee.printStackTrace();
            throw ee;
        }
    }

    private Instant extractTokenExpiration(String token) throws Exception {
        Jwt claimsJws = jwtUtil.validateJwtToken(token, decoder);
        return claimsJws.getExpiresAt();
    }
}

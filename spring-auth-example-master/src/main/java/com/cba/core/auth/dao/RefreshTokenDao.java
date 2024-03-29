package com.cba.core.auth.dao;

import com.cba.core.auth.model.TokenRefresh;

import java.io.IOException;
import java.util.Optional;

public interface RefreshTokenDao {

    Optional<TokenRefresh> findByToken(String token) throws Exception;

    TokenRefresh createRefreshToken(TokenRefresh refreshToken) throws IOException;

    int deleteByUserId(Integer userId) throws Exception;

    void deleteByToken(TokenRefresh token);

}

package com.cba.core.auth.service;

import com.cba.core.auth.model.TokenBlacklist;

public interface TokenBlacklistService {

    TokenBlacklist createBlacklistToken(String token) throws Exception;

    boolean isTokenBlacklisted(String token) throws Exception;
}

package com.cba.core.auth.dao;

import com.cba.core.auth.model.TokenBlacklist;

public interface TokenBlacklistDao {

     TokenBlacklist create(TokenBlacklist token) throws Exception;

     TokenBlacklist findByToken(String token) throws Exception;

}

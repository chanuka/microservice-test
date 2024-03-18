package com.cba.core.auth.dao.impl;

import com.cba.core.auth.dao.TokenBlacklistDao;
import com.cba.core.auth.model.TokenBlacklist;
import com.cba.core.auth.repository.TokenBlacklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TokenBlacklistDaoImpl implements TokenBlacklistDao {

    private final TokenBlacklistRepository repository;

    @Override
    public TokenBlacklist create(TokenBlacklist token) throws Exception {
        return repository.save(token);
    }

    @Override
    public TokenBlacklist findByToken(String token) throws Exception {
        return repository.findByToken(token).orElse(null);
    }


}

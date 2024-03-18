package com.cba.core.auth.repository;

import com.cba.core.auth.model.TokenRefresh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<TokenRefresh, Long> {
    Optional<TokenRefresh> findByToken(String token);

    int deleteByUserId(Integer userId);
}

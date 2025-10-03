package com.batch.batchProcessing.auth.repository;


import com.batch.batchProcessing.auth.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshTokenEntity, UUID> {
    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);

}

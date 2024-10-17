package com.communicators.welltalk.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.communicators.welltalk.Entity.ReferralTokenEntity;

import java.util.Optional;

public interface ReferralTokenRepository extends JpaRepository<ReferralTokenEntity, Integer> {
    Optional<ReferralTokenEntity> findByToken(String token);

    ReferralTokenEntity findByReferral_ReferralId(int referralId);

}

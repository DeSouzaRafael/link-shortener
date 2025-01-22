package com.example.linkShortener.adapters.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataShortLinkRepository extends JpaRepository<ShortLinkEntity, Long> {
    Optional<ShortLinkEntity> findByShortCode(String shortCode);
}
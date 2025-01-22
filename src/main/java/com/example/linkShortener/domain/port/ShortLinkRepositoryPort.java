package com.example.linkShortener.domain.port;

import java.util.List;
import java.util.Optional;

import com.example.linkShortener.domain.model.ShortLink;

public interface ShortLinkRepositoryPort {

    ShortLink save(ShortLink shortLink);
    Optional<ShortLink> findById(Long id);
    Optional<ShortLink> findByShortCode(String shortCode);
    List<ShortLink> findAll();
    void deleteById(Long id);
}

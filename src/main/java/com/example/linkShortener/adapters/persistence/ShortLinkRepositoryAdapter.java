package com.example.linkShortener.adapters.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.linkShortener.domain.model.ShortLink;
import com.example.linkShortener.domain.port.ShortLinkRepositoryPort;

@Component
public class ShortLinkRepositoryAdapter implements ShortLinkRepositoryPort {

    private final SpringDataShortLinkRepository springDataRepo;

    public ShortLinkRepositoryAdapter(SpringDataShortLinkRepository springDataRepo) {
        this.springDataRepo = springDataRepo;
    }

    @Override
    public ShortLink save(ShortLink shortLink) {
        ShortLinkEntity entity = toEntity(shortLink);
        ShortLinkEntity saved = springDataRepo.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<ShortLink> findById(Long id) {
        return springDataRepo.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<ShortLink> findByShortCode(String shortCode) {
        return springDataRepo.findByShortCode(shortCode).map(this::toDomain);
    }

    @Override
    public List<ShortLink> findAll() {
        return springDataRepo.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        springDataRepo.deleteById(id);
    }

    private ShortLinkEntity toEntity(ShortLink shortLink) {
        ShortLinkEntity entity = new ShortLinkEntity();
        entity.setId(shortLink.getId());
        entity.setOriginalUrl(shortLink.getOriginalUrl());
        entity.setShortCode(shortLink.getShortCode());
        entity.setCreatedAt(shortLink.getCreatedAt());
        return entity;
    }

    private ShortLink toDomain(ShortLinkEntity entity) {
        return new ShortLink(
            entity.getId(),
            entity.getOriginalUrl(),
            entity.getShortCode(),
            entity.getCreatedAt()
        );
    }
}
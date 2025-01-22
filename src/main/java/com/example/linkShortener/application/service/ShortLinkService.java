package com.example.linkShortener.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.linkShortener.domain.model.ShortLink;
import com.example.linkShortener.domain.port.ShortLinkRepositoryPort;

@Service
public class ShortLinkService {

    private final ShortLinkRepositoryPort repositoryPort;

    public ShortLinkService(ShortLinkRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    public ShortLink createShortLink(String originalUrl) {
        String shortCode = generateShortCode();
        ShortLink link = new ShortLink(null, originalUrl, shortCode, LocalDateTime.now());
        return repositoryPort.save(link);
    }

    public Optional<ShortLink> getById(Long id) {
        return repositoryPort.findById(id);
    }

    public Optional<ShortLink> getByShortCode(String code) {
        return repositoryPort.findByShortCode(code);
    }

    public List<ShortLink> getAll() {
        return repositoryPort.findAll();
    }

    public Optional<ShortLink> updateShortLink(Long id, String newUrl) {
        Optional<ShortLink> existing = repositoryPort.findById(id);
        if (existing.isPresent()) {
            ShortLink link = existing.get();
            link.setOriginalUrl(newUrl);
            repositoryPort.save(link);
            return Optional.of(link);
        }
        return Optional.empty();
    }

    public void deleteShortLink(Long id) {
        repositoryPort.deleteById(id);
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
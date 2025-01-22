package com.example.linkShortener.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class ShortLink {

    private Long id;
    private String originalUrl;
    private String shortCode;
    private LocalDateTime createdAt;

    public ShortLink(Long id, String originalUrl, String shortCode, LocalDateTime createdAt) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShortLink)) {
            return false;
        }
        ShortLink shortLink = (ShortLink) o;
        return Objects.equals(id, shortLink.id)
                && Objects.equals(originalUrl, shortLink.originalUrl)
                && Objects.equals(shortCode, shortLink.shortCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, originalUrl, shortCode);
    }
}

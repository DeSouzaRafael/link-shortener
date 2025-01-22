package com.example.linkShortener.application.service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.linkShortener.domain.model.ShortLink;
import com.example.linkShortener.domain.port.ShortLinkRepositoryPort;

class ShortLinkServiceTest {

    private ShortLinkRepositoryPort repositoryPort;
    private ShortLinkService service;

    @BeforeEach
    void setup() {
        repositoryPort = Mockito.mock(ShortLinkRepositoryPort.class);
        service = new ShortLinkService(repositoryPort);
    }

    @Test
    void testCreateShortLink() {
        String originalUrl = "https://www.google.com";
        when(repositoryPort.save(any(ShortLink.class)))
                .thenAnswer(invocation -> {
                    ShortLink link = invocation.getArgument(0);
                    link.setId(1L);
                    return link;
                });

        ShortLink created = service.createShortLink(originalUrl);

        assertNotNull(created);
        assertEquals(1L, created.getId());
        assertEquals(originalUrl, created.getOriginalUrl());
        assertNotNull(created.getShortCode());
        verify(repositoryPort, times(1)).save(any(ShortLink.class));
    }

    @Test
    void testGetById_found() {
        ShortLink link = new ShortLink(1L, "https://www.google.com", "abc123", LocalDateTime.now());
        when(repositoryPort.findById(1L)).thenReturn(Optional.of(link));

        Optional<ShortLink> result = service.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("abc123", result.get().getShortCode());
        verify(repositoryPort, times(1)).findById(1L);
    }

    @Test
    void testGetById_notFound() {
        when(repositoryPort.findById(999L)).thenReturn(Optional.empty());

        Optional<ShortLink> result = service.getById(999L);

        assertFalse(result.isPresent());
        verify(repositoryPort, times(1)).findById(999L);
    }

    @Test
    void testUpdateShortLink_whenExists() {
        ShortLink existing = new ShortLink(1L, "https://old-url.com", "abc123", LocalDateTime.now());
        when(repositoryPort.findById(1L)).thenReturn(Optional.of(existing));
        when(repositoryPort.save(any(ShortLink.class))).thenReturn(existing);

        Optional<ShortLink> updatedOpt = service.updateShortLink(1L, "https://new-url.com");

        assertTrue(updatedOpt.isPresent());
        ShortLink updated = updatedOpt.get();
        assertEquals("https://new-url.com", updated.getOriginalUrl());
        verify(repositoryPort, times(1)).findById(1L);
        verify(repositoryPort, times(1)).save(existing);
    }

    @Test
    void testUpdateShortLink_whenNotFound() {
        when(repositoryPort.findById(999L)).thenReturn(Optional.empty());

        Optional<ShortLink> updatedOpt = service.updateShortLink(999L, "https://new-url.com");

        assertFalse(updatedOpt.isPresent());
        verify(repositoryPort, times(1)).findById(999L);
        verify(repositoryPort, never()).save(any(ShortLink.class));
    }

    @Test
    void testDeleteShortLink() {
        service.deleteShortLink(10L);

        verify(repositoryPort, times(1)).deleteById(10L);
    }
}

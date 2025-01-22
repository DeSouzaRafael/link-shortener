package com.example.linkShortener.adapters.web;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.linkShortener.application.service.ShortLinkService;
import com.example.linkShortener.domain.model.ShortLink;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class ShortLinkController {

    private final ShortLinkService service;

    public ShortLinkController(ShortLinkService service) {
        this.service = service;
    }

    @Operation(summary = "Create a new record")
    @PostMapping
    public ResponseEntity<ShortLink> create(@RequestBody CreateLinkRequest request) {
        ShortLink created = service.createShortLink(request.originalUrl());
        return ResponseEntity.ok(created);
    }

    @Operation(summary = "Returns one record by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ShortLink> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "List all shortened links")
    @GetMapping
    public ResponseEntity<List<ShortLink>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Update a record")
    @PutMapping("/{id}")
    public ResponseEntity<ShortLink> update(@PathVariable Long id, @RequestBody UpdateLinkRequest request) {
        return service.updateShortLink(id, request.newUrl())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletes a link shortened by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteShortLink(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Redirects to the link")
    @GetMapping("/r/{shortCode}")
    public ResponseEntity<?> redirectByShortCode(@PathVariable String shortCode) {
        return service.getByShortCode(shortCode)
            .map(shortLink -> ResponseEntity
                    .status(HttpStatus.MOVED_PERMANENTLY)
                    .location(URI.create(shortLink.getOriginalUrl()))
                    .build()
            )
            .orElse(ResponseEntity.notFound().build());
    }

    public record CreateLinkRequest(String originalUrl) {}
    public record UpdateLinkRequest(String newUrl) {}
}

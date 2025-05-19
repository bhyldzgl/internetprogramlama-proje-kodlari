package com.habersitesi.controller;

import com.habersitesi.model.Haber;
import com.habersitesi.service.FavoriService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favori")
@RequiredArgsConstructor
public class FavoriController {

    private final FavoriService favoriService;

    @PostMapping("/{haberId}")
    @PreAuthorize("hasAnyRole('EDITOR','UYE','ADMIN')")
    public void favoriIslemi(@PathVariable Long haberId) {
        favoriService.favoriEkleVeyaCikar(haberId);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Haber> favoriHaberlerim() {
        return favoriService.favoriHaberlerimiGetir();
    }
}

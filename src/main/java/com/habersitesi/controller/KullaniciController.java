package com.habersitesi.controller;

import com.habersitesi.dto.KullaniciGuncelleRequest;
import com.habersitesi.dto.KullaniciResponse;
import com.habersitesi.service.KullaniciService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kullanici")
@RequiredArgsConstructor
public class KullaniciController {

    private final KullaniciService kullaniciService;

    @GetMapping("/profil")
    @PreAuthorize("isAuthenticated()")
    public KullaniciResponse profilimiGetir() {
        return kullaniciService.profilimiGetir();
    }

    @PutMapping("/profil")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> profilimiGuncelle(@RequestBody KullaniciGuncelleRequest request) {
        kullaniciService.profilimiGuncelle(request); // ← Bu metod arayüzde ve implemantasyonda olmalı
        return ResponseEntity.ok("Profil güncellendi.");
    }


}

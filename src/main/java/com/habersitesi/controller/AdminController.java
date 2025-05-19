package com.habersitesi.controller;

import com.habersitesi.dto.RolGuncelleRequest;
import com.habersitesi.model.Kullanici;
import com.habersitesi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/kullanicilar")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Kullanici> tumKullanicilariGetir() {
        return adminService.kullanicilariListele();
    }

    @PutMapping("/rol-guncelle")
    @PreAuthorize("hasRole('ADMIN')")
    public String rolDegistir(@RequestBody RolGuncelleRequest request) {
        adminService.rolDegistir(request);
        return "Kullanıcının rolü güncellendi.";
    }
    @DeleteMapping("/kullanici/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> kullaniciSil(@PathVariable Long id) {
        adminService.kullaniciSil(id);
        return ResponseEntity.ok("Kullanıcı başarıyla silindi.");
    }

}

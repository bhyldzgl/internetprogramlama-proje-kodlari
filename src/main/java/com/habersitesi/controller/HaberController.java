package com.habersitesi.controller;

import com.habersitesi.dto.HaberRequest;
import com.habersitesi.model.Haber;
import com.habersitesi.service.HaberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/haber")
@RequiredArgsConstructor
public class HaberController {

    private final HaberService haberService;

    @PostMapping("/yayinla")
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    public Haber yayinla(@RequestBody HaberRequest request) {
        return haberService.haberYayinla(request);
    }

    @GetMapping("/tum")
    public List<Haber> tumHaberler() {
        return haberService.tumHaberleriGetir();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    public Haber guncelle(@PathVariable Long id, @RequestBody HaberRequest request) {
        return haberService.haberiGuncelle(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    public void sil(@PathVariable Long id) {
        haberService.haberiSil(id);
    }

    @GetMapping("/ara")
    public List<Haber> haberAra(@RequestParam String kelime) {
        return haberService.haberAra(kelime);
    }

    @GetMapping("/kategori/{kategoriId}")
    public List<Haber> kategoriyeGore(@PathVariable Long kategoriId) {
        return haberService.kategoriyeGoreGetir(kategoriId);
    }

    @GetMapping("/yazar/{email}")
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    public List<Haber> yazaraGore(@PathVariable String email) {
        return haberService.yazaraGoreGetir(email);
    }
    @GetMapping("/populer")
    public List<Haber> populerHaberler(@RequestParam(defaultValue = "10") int limit) {
        return haberService.populerHaberleriGetir(limit);
    }
    @PostMapping(value = "/yayinla-gorselli", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR')")
    public ResponseEntity<Haber> yayinlaHaber(
            @RequestParam("baslik") String baslik,
            @RequestParam("icerik") String icerik,
            @RequestParam("kategoriId") Long kategoriId,
            @RequestParam(value = "gorsel", required = false) MultipartFile gorsel) throws IOException {

        Haber haber = haberService.haberYayinlaGorselli(baslik, icerik, kategoriId, gorsel);
        return ResponseEntity.ok(haber);
    }

    @GetMapping("/filtrele")
    public List<Haber> haberFiltrele(
            @RequestParam Long kategoriId,
            @RequestParam String kelime) {
        return haberService.kategoriVeKelimeyeGoreGetir(kategoriId, kelime);
    }
    @GetMapping("/etiket/{etiket}")
    public List<Haber> etiketeGore(@PathVariable String etiket) {
        return haberService.etiketeGoreGetir(etiket);
    }



}

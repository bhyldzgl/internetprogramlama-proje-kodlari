package com.habersitesi.controller;

import com.habersitesi.dto.YorumGuncelleRequest;
import com.habersitesi.dto.YorumRequest;
import com.habersitesi.model.Yorum;
import com.habersitesi.service.YorumService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/yorum")
@RequiredArgsConstructor
public class YorumController {

    private final YorumService yorumService;

    @PostMapping("/ekle")
    @PreAuthorize("hasAnyRole('EDITOR','UYE')")
    public Yorum yorumEkle(@RequestBody YorumRequest request) {
        return yorumService.yorumEkle(request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('EDITOR','UYE','ADMIN')")
    public void yorumSil(@PathVariable Long id) {
        yorumService.yorumSil(id);
    }

    @GetMapping("/haber/{haberId}")
    public List<Yorum> habereYorumlar(@PathVariable Long haberId) {
        return yorumService.habereAitYorumlariGetir(haberId);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EDITOR','UYE')")
    public Yorum yorumGuncelle(@PathVariable Long id, @RequestBody YorumGuncelleRequest request) {
        return yorumService.yorumGuncelle(id, request);
    }
    @GetMapping("/cevaplar/{yorumId}")
    public List<Yorum> yorumCevaplari(@PathVariable Long yorumId) {
        return yorumService.cevaplariGetir(yorumId);
    }


}

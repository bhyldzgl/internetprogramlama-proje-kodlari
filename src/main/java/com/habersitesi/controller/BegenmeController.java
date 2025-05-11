package com.habersitesi.controller;

import com.habersitesi.service.BegenmeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/begenme")
@RequiredArgsConstructor
public class BegenmeController {

    private final BegenmeService begenmeService;

    @PostMapping("/{haberId}")
    @PreAuthorize("hasAnyRole('UYE','EDITOR','ADMIN')")
    public String begenVeyaCikar(@PathVariable Long haberId) {
        return begenmeService.begenVeyaCikar(haberId);
    }

    @GetMapping("/sayisi/{haberId}")
    public Long begeniSayisi(@PathVariable Long haberId) {
        return begenmeService.haberBegenmeSayisi(haberId);
    }
}

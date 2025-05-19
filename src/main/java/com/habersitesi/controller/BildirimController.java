package com.habersitesi.controller;

import com.habersitesi.model.Bildirim;
import com.habersitesi.service.BildirimService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bildirim")
@RequiredArgsConstructor
public class BildirimController {

    private final BildirimService bildirimService;

    @GetMapping("/okunmayanlar")
    @PreAuthorize("isAuthenticated()")
    public List<Bildirim> okunmayanlar() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return bildirimService.okunmayanBildirimleriGetir(email);
    }
    @GetMapping("/tum")
    @PreAuthorize("isAuthenticated()")
    public List<Bildirim> tumBildirimler() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return bildirimService.tumBildirimleriGetir(email);
    }

}

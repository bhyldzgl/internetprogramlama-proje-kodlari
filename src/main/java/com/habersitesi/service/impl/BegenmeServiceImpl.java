package com.habersitesi.service.impl;

import com.habersitesi.exception.HaberBulunamadiException;
import com.habersitesi.exception.KullaniciBulunamadiException;
import com.habersitesi.model.*;
import com.habersitesi.repository.*;
import com.habersitesi.service.BegenmeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BegenmeServiceImpl implements BegenmeService {

    private final BegenmeRepository begenmeRepository;
    private final KullaniciRepository kullaniciRepository;
    private final HaberRepository haberRepository;

    @Override
    public String begenVeyaCikar(Long haberId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new KullaniciBulunamadiException(email));

        Haber haber = haberRepository.findById(haberId)
                .orElseThrow(() -> new HaberBulunamadiException(haberId));

        return begenmeRepository.findByKullaniciAndHaber(kullanici, haber)
                .map(begenme -> {
                    begenmeRepository.delete(begenme);
                    return "Beğeni kaldırıldı.";
                })
                .orElseGet(() -> {
                    begenmeRepository.save(new Begenme(null, kullanici, haber));
                    return "Haber beğenildi.";
                });
    }

    @Override
    public Long haberBegenmeSayisi(Long haberId) {
        Haber haber = haberRepository.findById(haberId)
                .orElseThrow(() -> new HaberBulunamadiException(haberId));
        return begenmeRepository.countByHaber(haber);
    }

}

package com.habersitesi.service.impl;

import com.habersitesi.exception.HaberBulunamadiException;
import com.habersitesi.exception.KullaniciBulunamadiException;
import com.habersitesi.model.*;
import com.habersitesi.repository.*;
import com.habersitesi.service.FavoriService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriServiceImpl implements FavoriService {

    private final FavoriRepository favoriRepository;
    private final HaberRepository haberRepository;
    private final KullaniciRepository kullaniciRepository;

    @Override
    public void favoriEkleVeyaCikar(Long haberId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new KullaniciBulunamadiException(email));

        Haber haber = haberRepository.findById(haberId)
                .orElseThrow(() -> new HaberBulunamadiException(haberId));

        favoriRepository.findByKullaniciAndHaber(kullanici, haber).ifPresentOrElse(
                favoriRepository::delete,
                () -> favoriRepository.save(new Favori(null, kullanici, haber))
        );
    }

    @Override
    public List<Haber> favoriHaberlerimiGetir() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new KullaniciBulunamadiException(email));

        return favoriRepository.findAllByKullanici(kullanici)
                .stream().map(Favori::getHaber).toList();
    }
}

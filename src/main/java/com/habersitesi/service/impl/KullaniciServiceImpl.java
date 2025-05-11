package com.habersitesi.service.impl;

import com.habersitesi.dto.KullaniciGuncelleRequest;
import com.habersitesi.dto.KullaniciResponse;
import com.habersitesi.exception.KullaniciBulunamadiException;
import com.habersitesi.model.Kullanici;
import com.habersitesi.repository.KullaniciRepository;
import com.habersitesi.service.KullaniciService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KullaniciServiceImpl implements KullaniciService {

    private final KullaniciRepository kullaniciRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @Override
    public KullaniciResponse profilimiGetir() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new KullaniciBulunamadiException(email));

        KullaniciResponse response = new KullaniciResponse();
        response.setAdSoyad(kullanici.getAdSoyad());
        response.setEmail(kullanici.getEmail());
        response.setRoller(
                kullanici.getRoller().stream()
                        .map(r -> r.getAd().name())
                        .collect(Collectors.toSet())
        );

        return response;
    }

    @Override
    public void profilimiGuncelle(KullaniciGuncelleRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new KullaniciBulunamadiException(email));

        if (request.getAdSoyad() != null && !request.getAdSoyad().isBlank()) {
            kullanici.setAdSoyad(request.getAdSoyad());
        }

        if (request.getSifre() != null && !request.getSifre().isBlank()) {
            String sifreli = passwordEncoder.encode(request.getSifre());
            kullanici.setSifre(sifreli);
        }

        kullaniciRepository.save(kullanici);
    }


}

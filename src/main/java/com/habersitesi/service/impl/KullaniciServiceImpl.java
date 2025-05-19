package com.habersitesi.service.impl;

import com.habersitesi.dto.KullaniciGuncelleRequest;
import com.habersitesi.dto.KullaniciResponse;
import com.habersitesi.dto.SifreDegistirmeRequest;
import com.habersitesi.exception.KullaniciBulunamadiException;
import com.habersitesi.exception.YetkisizIslemException;
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
    @Override
    public void sifreDegistir(SifreDegistirmeRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new KullaniciBulunamadiException(email));

        if (!passwordEncoder.matches(request.getMevcutSifre(), kullanici.getSifre())) {
            throw new YetkisizIslemException("Mevcut şifre hatalı.");
        }

        if (request.getYeniSifre() == null || request.getYeniSifre().isBlank()) {
            throw new RuntimeException("Yeni şifre boş olamaz.");
        }

        if (passwordEncoder.matches(request.getYeniSifre(), kullanici.getSifre())) {
            throw new RuntimeException("Yeni şifre, mevcut şifre ile aynı olamaz.");
        }

        kullanici.setSifre(passwordEncoder.encode(request.getYeniSifre()));
        kullaniciRepository.save(kullanici);
    }



}

package com.habersitesi.service.impl;

import com.habersitesi.dto.HaberRequest;
import com.habersitesi.exception.HaberBulunamadiException;
import com.habersitesi.exception.KullaniciBulunamadiException;
import com.habersitesi.exception.YetkisizIslemException;
import com.habersitesi.model.*;
import com.habersitesi.repository.*;
import com.habersitesi.service.HaberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HaberServiceImpl implements HaberService {

    private final HaberRepository haberRepository;
    private final KullaniciRepository kullaniciRepository;
    private final KategoriRepository kategoriRepository;

    @Override
    public Haber haberYayinla(HaberRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kullanici yazar = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Yazar bulunamadı"));

        Kategori kategori = kategoriRepository.findById(request.getKategoriId())
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"));

        Haber haber = new Haber();
        haber.setBaslik(request.getBaslik());
        haber.setIcerik(request.getIcerik());
        haber.setYayinTarihi(LocalDateTime.now());
        haber.setKategori(kategori);
        haber.setYazar(yazar);

        return haberRepository.save(haber);
    }

    @Override
    public List<Haber> tumHaberleriGetir() {
        return haberRepository.findAll();
    }

    @Override
    public Haber haberiGuncelle(Long id, HaberRequest request) {
        Haber haber = haberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Haber bulunamadı"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean yetkili = haber.getYazar().getEmail().equals(email) ||
                kullaniciRepository.findByEmail(email).get().getRoller().stream()
                        .anyMatch(r -> r.getAd() == Rol.RolTipi.ADMIN);

        if (!yetkili) throw new RuntimeException("Bu haberi güncelleme yetkiniz yok.");

        haber.setBaslik(request.getBaslik());
        haber.setIcerik(request.getIcerik());
        return haberRepository.save(haber);
    }

    @Override
    public void haberiSil(Long id) {
        Haber haber = haberRepository.findById(id)
                .orElseThrow(() -> new HaberBulunamadiException(id));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean yetkili = haber.getYazar().getEmail().equals(email) ||
                kullaniciRepository.findByEmail(email).get().getRoller().stream()
                        .anyMatch(r -> r.getAd() == Rol.RolTipi.ADMIN);

        if (!yetkili) throw new YetkisizIslemException("Bu haberi silme yetkiniz yok.");

        haberRepository.delete(haber);
    }

    @Override
    public List<Haber> haberAra(String kelime) {
        return haberRepository.findByBaslikContainingIgnoreCaseOrIcerikContainingIgnoreCase(kelime, kelime);
    }

    @Override
    public List<Haber> kategoriyeGoreGetir(Long kategoriId) {
        Kategori kategori = kategoriRepository.findById(kategoriId)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"));
        return haberRepository.findByKategori(kategori);
    }

    @Override
    public List<Haber> yazaraGoreGetir(String email) {
        Kullanici yazar = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Yazar bulunamadı"));
        return haberRepository.findByYazar(yazar);
    }


}

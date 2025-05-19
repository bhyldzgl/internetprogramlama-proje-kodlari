package com.habersitesi.service.impl;

import com.habersitesi.dto.HaberRequest;
import com.habersitesi.exception.HaberBulunamadiException;
import com.habersitesi.exception.KategoriBulunamadiException;
import com.habersitesi.exception.KullaniciBulunamadiException;
import com.habersitesi.exception.YetkisizIslemException;
import com.habersitesi.model.*;
import com.habersitesi.repository.*;
import com.habersitesi.service.HaberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HaberServiceImpl implements HaberService {

    private final HaberRepository haberRepository;
    private final KullaniciRepository kullaniciRepository;
    private final KategoriRepository kategoriRepository;
    private final EtiketRepository etiketRepository;

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

        if (request.getEtiketler() != null) {
            Set<Etiket> etiketSet = new HashSet<>();
            for (String etiketAd : request.getEtiketler()) {
                Etiket etiket = etiketRepository.findByAdIgnoreCase(etiketAd)
                        .orElseGet(() -> new Etiket(null, etiketAd, new HashSet<>()));
                etiketSet.add(etiket);
            }
            haber.setEtiketler(etiketSet);
        }

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

        if (request.getEtiketler() != null) {
            Set<Etiket> etiketSet = new HashSet<>();
            for (String etiketAd : request.getEtiketler()) {
                Etiket etiket = etiketRepository.findByAdIgnoreCase(etiketAd)
                        .orElseGet(() -> new Etiket(null, etiketAd, new HashSet<>()));
                etiketSet.add(etiket);
            }
            haber.setEtiketler(etiketSet);
        }

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

    @Override
    public List<Haber> populerHaberleriGetir(int limit) {
        return haberRepository.findPopulerHaberler(PageRequest.of(0, limit));
    }

    @Override
    public Haber haberYayinlaGorselli(String baslik, String icerik, Long kategoriId, MultipartFile gorsel) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kullanici yazar = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new KullaniciBulunamadiException(email));

        Kategori kategori = kategoriRepository.findById(kategoriId)
                .orElseThrow(() -> new KategoriBulunamadiException(kategoriId));

        Haber haber = new Haber();
        haber.setBaslik(baslik);
        haber.setIcerik(icerik);
        haber.setYayinTarihi(LocalDateTime.now());
        haber.setKategori(kategori);
        haber.setYazar(yazar);

        if (gorsel != null && !gorsel.isEmpty()) {
            String dosyaAdi = UUID.randomUUID() + "_" + gorsel.getOriginalFilename();
            Path uploadPath = Paths.get("uploads");
            Files.createDirectories(uploadPath);

            Path dosyaYolu = uploadPath.resolve(dosyaAdi);
            Files.copy(gorsel.getInputStream(), dosyaYolu, StandardCopyOption.REPLACE_EXISTING);

            haber.setGorselYolu("/uploads/" + dosyaAdi);
        }

        return haberRepository.save(haber);
    }

    @Override
    public List<Haber> kategoriVeKelimeyeGoreGetir(Long kategoriId, String kelime) {
        return haberRepository.araKategoriyeGore(kategoriId, kelime);
    }
    @Override
    public List<Haber> etiketeGoreGetir(String etiket) {
        return haberRepository.findByEtiket(etiket);
    }

}

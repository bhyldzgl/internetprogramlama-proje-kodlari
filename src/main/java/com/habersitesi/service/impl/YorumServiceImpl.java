package com.habersitesi.service.impl;

import com.habersitesi.dto.YorumGuncelleRequest;
import com.habersitesi.dto.YorumRequest;
import com.habersitesi.exception.HaberBulunamadiException;
import com.habersitesi.exception.KullaniciBulunamadiException;
import com.habersitesi.exception.YetkisizIslemException;
import com.habersitesi.model.*;
import com.habersitesi.repository.*;
import com.habersitesi.service.BildirimService;
import com.habersitesi.service.YorumService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YorumServiceImpl implements YorumService {

    private final YorumRepository yorumRepository;
    private final KullaniciRepository kullaniciRepository;
    private final HaberRepository haberRepository;
    private final BildirimService bildirimService;


    @Override
    public Yorum yorumEkle(YorumRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new KullaniciBulunamadiException(email));

        Haber haber = haberRepository.findById(request.getHaberId())
                .orElseThrow(() -> new HaberBulunamadiException(request.getHaberId()));

        Yorum yorum = new Yorum();
        yorum.setIcerik(request.getIcerik());
        yorum.setYorumTarihi(LocalDateTime.now());
        yorum.setKullanici(kullanici);
        yorum.setHaber(haber);

        if (request.getParentYorumId() != null) {
            Yorum parent = yorumRepository.findById(request.getParentYorumId())
                    .orElseThrow(() -> new RuntimeException("Cevap verilen yorum bulunamadı: " + request.getParentYorumId()));
            yorum.setParentYorum(parent);

            // Bildirim gönder
            Kullanici hedef = parent.getKullanici();
            if (!hedef.getEmail().equals(email)) {
                bildirimService.bildirimEkle(hedef, "Yorumuna cevap geldi.");
            }
        }

        return yorumRepository.save(yorum);
    }



    @Override
    public void yorumSil(Long yorumId) {
        Yorum yorum = yorumRepository.findById(yorumId)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı: " + yorumId));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kullanici aktifKullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new KullaniciBulunamadiException(email));

        boolean adminMi = aktifKullanici.getRoller().stream()
                .anyMatch(r -> r.getAd() == Rol.RolTipi.ADMIN);

        if (!adminMi && !yorum.getKullanici().getEmail().equals(email)) {
            throw new YetkisizIslemException("Bu yorumu silme yetkiniz yok.");
        }

        yorumRepository.delete(yorum);
    }

    @Override
    public List<Yorum> habereAitYorumlariGetir(Long haberId) {
        Haber haber = haberRepository.findById(haberId)
                .orElseThrow(() -> new HaberBulunamadiException(haberId));
        return yorumRepository.findByHaber(haber);
    }
    @Override
    public Yorum yorumGuncelle(Long yorumId, YorumGuncelleRequest request) {
        Yorum yorum = yorumRepository.findById(yorumId)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı: " + yorumId));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Kullanici aktifKullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(() -> new KullaniciBulunamadiException(email));

        boolean adminMi = aktifKullanici.getRoller().stream()
                .anyMatch(r -> r.getAd() == Rol.RolTipi.ADMIN);

        if (!adminMi && !yorum.getKullanici().getEmail().equals(email)) {
            throw new YetkisizIslemException("Bu yorumu güncelleme yetkiniz yok.");
        }

        yorum.setIcerik(request.getIcerik());
        yorum.setYorumTarihi(LocalDateTime.now());

        return yorumRepository.save(yorum);
    }
    @Override
    public List<Yorum> cevaplariGetir(Long yorumId) {
        Yorum parent = yorumRepository.findById(yorumId)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı: " + yorumId));
        return parent.getCevaplar();
    }


}


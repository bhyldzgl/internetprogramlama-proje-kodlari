package com.habersitesi.service.impl;

import com.habersitesi.dto.RolGuncelleRequest;
import com.habersitesi.exception.KullaniciBulunamadiException;
import com.habersitesi.model.Kullanici;
import com.habersitesi.model.Rol;
import com.habersitesi.repository.KullaniciRepository;
import com.habersitesi.repository.RolRepository;
import com.habersitesi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final KullaniciRepository kullaniciRepository;
    private final RolRepository rolRepository;

    @Override
    public List<Kullanici> kullanicilariListele() {
        return kullaniciRepository.findAll();
    }

    @Override
    public void rolDegistir(RolGuncelleRequest request) {
        Kullanici k = kullaniciRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new KullaniciBulunamadiException(request.getEmail()));

        Rol yeniRol = rolRepository.findByAd(Rol.RolTipi.valueOf(request.getYeniRol()))
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı: " + request.getYeniRol()));

        k.setRoller(Set.of(yeniRol));
        kullaniciRepository.save(k);
    }
    @Override
    public void kullaniciSil(Long kullaniciId) {
        Kullanici kullanici = kullaniciRepository.findById(kullaniciId)
                .orElseThrow(() -> new KullaniciBulunamadiException("ID: " + kullaniciId));

        kullaniciRepository.delete(kullanici);
    }

}

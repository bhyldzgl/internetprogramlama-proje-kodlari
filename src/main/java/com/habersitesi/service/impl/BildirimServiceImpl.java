package com.habersitesi.service.impl;

import com.habersitesi.model.Bildirim;
import com.habersitesi.model.Kullanici;
import com.habersitesi.repository.BildirimRepository;
import com.habersitesi.service.BildirimService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BildirimServiceImpl implements BildirimService {

    private final BildirimRepository bildirimRepository;

    @Override
    public void bildirimEkle(Kullanici hedefKullanici, String mesaj) {
        Bildirim bildirim = new Bildirim();
        bildirim.setHedefKullanici(hedefKullanici);
        bildirim.setMesaj(mesaj);
        bildirim.setTarih(LocalDateTime.now());
        bildirimRepository.save(bildirim);
    }

    @Override
    public List<Bildirim> okunmayanBildirimleriGetir(String email) {
        return bildirimRepository.findByHedefKullaniciEmailAndOkunduFalse(email);
    }
    @Override
    public List<Bildirim> tumBildirimleriGetir(String email) {
        return bildirimRepository.findByHedefKullaniciEmailOrderByTarihDesc(email);
    }

}

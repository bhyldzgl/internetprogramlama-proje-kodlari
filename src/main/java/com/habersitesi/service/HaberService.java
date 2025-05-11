package com.habersitesi.service;

import com.habersitesi.dto.HaberRequest;
import com.habersitesi.model.Haber;

import java.util.List;

public interface HaberService {
    Haber haberYayinla(HaberRequest request);
    List<Haber> tumHaberleriGetir();
    Haber haberiGuncelle(Long id, HaberRequest request);
    void haberiSil(Long id);
    List<Haber> haberAra(String kelime);
    List<Haber> kategoriyeGoreGetir(Long kategoriId);
    List<Haber> yazaraGoreGetir(String email);
}

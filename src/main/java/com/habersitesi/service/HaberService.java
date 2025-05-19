package com.habersitesi.service;

import com.habersitesi.dto.HaberRequest;
import com.habersitesi.model.Haber;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HaberService {
    Haber haberYayinla(HaberRequest request);
    List<Haber> tumHaberleriGetir();
    Haber haberiGuncelle(Long id, HaberRequest request);
    void haberiSil(Long id);
    List<Haber> haberAra(String kelime);
    List<Haber> kategoriyeGoreGetir(Long kategoriId);
    List<Haber> yazaraGoreGetir(String email);
    List<Haber> populerHaberleriGetir(int limit);
    Haber haberYayinlaGorselli(String baslik, String icerik, Long kategoriId, org.springframework.web.multipart.MultipartFile gorsel) throws IOException;
    List<Haber> kategoriVeKelimeyeGoreGetir(Long kategoriId, String kelime);
    List<Haber> etiketeGoreGetir(String etiket);



}

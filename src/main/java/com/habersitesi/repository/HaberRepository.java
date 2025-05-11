package com.habersitesi.repository;

import com.habersitesi.model.Haber;
import com.habersitesi.model.Kategori;
import com.habersitesi.model.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HaberRepository extends JpaRepository<Haber, Long> {
    List<Haber> findByYazar(Kullanici yazar);
    List<Haber> findByBaslikContainingIgnoreCaseOrIcerikContainingIgnoreCase(String baslik, String icerik);
    List<Haber> findByKategori(Kategori kategori);
}

package com.habersitesi.repository;

import com.habersitesi.model.Haber;
import com.habersitesi.model.Kategori;
import com.habersitesi.model.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface HaberRepository extends JpaRepository<Haber, Long> {
    List<Haber> findByYazar(Kullanici yazar);
    List<Haber> findByBaslikContainingIgnoreCaseOrIcerikContainingIgnoreCase(String baslik, String icerik);
    List<Haber> findByKategori(Kategori kategori);
    @Query("SELECT h FROM Haber h LEFT JOIN Begenme b ON h = b.haber GROUP BY h.id ORDER BY COUNT(b.id) DESC")
    List<Haber> findPopulerHaberler(Pageable pageable);
    @Query("SELECT h FROM Haber h WHERE h.kategori.id = :kategoriId AND h.baslik LIKE %:kelime%")
    List<Haber> araKategoriyeGore(@Param("kategoriId") Long kategoriId, @Param("kelime") String kelime);
    @Query("SELECT h FROM Haber h JOIN h.etiketler e WHERE LOWER(e.ad) = LOWER(:etiket)")
    List<Haber> findByEtiket(String etiket);


}
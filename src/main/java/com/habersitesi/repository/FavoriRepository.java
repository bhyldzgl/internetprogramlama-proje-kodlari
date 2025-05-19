package com.habersitesi.repository;

import com.habersitesi.model.Favori;
import com.habersitesi.model.Haber;
import com.habersitesi.model.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriRepository extends JpaRepository<Favori, Long> {
    Optional<Favori> findByKullaniciAndHaber(Kullanici kullanici, Haber haber);
    List<Favori> findAllByKullanici(Kullanici kullanici);
}

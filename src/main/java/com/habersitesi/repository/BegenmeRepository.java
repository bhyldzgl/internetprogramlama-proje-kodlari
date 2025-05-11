package com.habersitesi.repository;

import com.habersitesi.model.Begenme;
import com.habersitesi.model.Haber;
import com.habersitesi.model.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BegenmeRepository extends JpaRepository<Begenme, Long> {
    Optional<Begenme> findByKullaniciAndHaber(Kullanici kullanici, Haber haber);
    Long countByHaber(Haber haber);
}

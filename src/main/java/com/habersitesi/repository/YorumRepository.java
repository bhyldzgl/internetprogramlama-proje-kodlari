package com.habersitesi.repository;

import com.habersitesi.model.Yorum;
import com.habersitesi.model.Haber;
import com.habersitesi.model.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface YorumRepository extends JpaRepository<Yorum, Long> {
    List<Yorum> findByHaber(Haber haber);
    List<Yorum> findByKullanici(Kullanici kullanici);
}

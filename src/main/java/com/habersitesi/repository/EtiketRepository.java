package com.habersitesi.repository;

import com.habersitesi.model.Etiket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EtiketRepository extends JpaRepository<Etiket, Long> {
    Optional<Etiket> findByAdIgnoreCase(String ad);
}

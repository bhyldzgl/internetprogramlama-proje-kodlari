package com.habersitesi.repository;

import com.habersitesi.model.Bildirim;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BildirimRepository extends JpaRepository<Bildirim, Long> {
    List<Bildirim> findByHedefKullaniciEmailAndOkunduFalse(String email);
    List<Bildirim> findByHedefKullaniciEmailOrderByTarihDesc(String email);

}

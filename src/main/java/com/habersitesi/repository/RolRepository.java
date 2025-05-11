package com.habersitesi.repository;

import com.habersitesi.model.Rol;
import com.habersitesi.model.Rol.RolTipi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByAd(RolTipi ad);
}

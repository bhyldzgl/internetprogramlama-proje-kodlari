package com.habersitesi.service;

import com.habersitesi.dto.RolGuncelleRequest;
import com.habersitesi.model.Kullanici;

import java.util.List;

public interface AdminService {
    List<Kullanici> kullanicilariListele();
    void rolDegistir(RolGuncelleRequest request);
    void kullaniciSil(Long kullaniciId);

}

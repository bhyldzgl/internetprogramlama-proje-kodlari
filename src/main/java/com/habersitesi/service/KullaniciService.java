package com.habersitesi.service;

import com.habersitesi.dto.KullaniciResponse;
import com.habersitesi.dto.KullaniciGuncelleRequest;

public interface KullaniciService {
    KullaniciResponse profilimiGetir();
    void profilimiGuncelle(KullaniciGuncelleRequest request); // ← Bu satır EKLİ olacak
}

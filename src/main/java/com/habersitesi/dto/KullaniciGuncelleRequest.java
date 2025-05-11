package com.habersitesi.dto;

import lombok.Data;

@Data
public class KullaniciGuncelleRequest {
    private String adSoyad;
    private String sifre; // yeni şifre
}

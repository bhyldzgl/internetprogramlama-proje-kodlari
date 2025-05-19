package com.habersitesi.service;

import com.habersitesi.model.Bildirim;
import com.habersitesi.model.Kullanici;

import java.util.List;

public interface BildirimService {
    void bildirimEkle(Kullanici hedefKullanici, String mesaj);
    List<Bildirim> okunmayanBildirimleriGetir(String email);
    List<Bildirim> tumBildirimleriGetir(String email);

}

package com.habersitesi.service;

import com.habersitesi.model.Haber;

import java.util.List;

public interface FavoriService {
    void favoriEkleVeyaCikar(Long haberId);
    List<Haber> favoriHaberlerimiGetir();
}

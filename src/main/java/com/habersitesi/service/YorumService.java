package com.habersitesi.service;

import com.habersitesi.dto.YorumRequest;
import com.habersitesi.model.Yorum;

import java.util.List;

public interface YorumService {
    Yorum yorumEkle(YorumRequest request);
    void yorumSil(Long yorumId);
    List<Yorum> habereAitYorumlariGetir(Long haberId);
}

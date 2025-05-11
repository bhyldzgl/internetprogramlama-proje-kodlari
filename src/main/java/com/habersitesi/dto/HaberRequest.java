package com.habersitesi.dto;

import lombok.Data;

@Data
public class HaberRequest {
    private String baslik;
    private String icerik;
    private Long kategoriId;
}

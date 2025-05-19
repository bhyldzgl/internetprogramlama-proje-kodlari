package com.habersitesi.dto;

import lombok.Data;
import java.util.List;

@Data
public class HaberRequest {
    private String baslik;
    private String icerik;
    private Long kategoriId;
    private List<String> etiketler; // ← Yeni alan
}
